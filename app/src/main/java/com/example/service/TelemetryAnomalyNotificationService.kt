package com.example.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.MainActivity
import com.example.R
import com.example.model.TelemetryAnomalyAlert
import com.example.model.ThreatSeverity

/**
 * Real-time Android System Notification Service for high-risk telemetry anomalies.
 * Dispatches Heads-Up priority notifications with cryptographic proof digests,
 * risk scoring metrics, and deep-link PendingIntents to the AGIS-2045 dashboard.
 */
class TelemetryAnomalyNotificationService(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "agis_telemetry_anomaly_channel"
        const val CHANNEL_NAME = "AGIS Telemetry Anomaly Sentinel"
        const val CHANNEL_DESCRIPTION = "Real-time high-risk anomaly alerts detected during telemetry sanitization."
        private const val BASE_NOTIFICATION_ID = 4096
    }

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                lightColor = 0xFFFF2A55.toInt() // Containment Crimson
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 150, 80, 150, 100, 300)
                setShowBadge(true)
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun postAnomalyNotification(alert: TelemetryAnomalyAlert) {
        try {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("EXTRA_NAVIGATE_TAB", 0) // Overview Dashboard
                putExtra("EXTRA_ANOMALY_ID", alert.id)
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                alert.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
            )

            val isCritical = alert.severity == ThreatSeverity.CRITICAL
            val riskPercentage = (alert.riskScore * 100).toInt()
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val bigTextStyle = NotificationCompat.BigTextStyle()
                .setBigContentTitle("⚠️ ${alert.title} [$riskPercentage% RISK]")
                .bigText(
                    "${alert.description}\n\n" +
                    "• Anomaly Vector: ${alert.anomalyType.label}\n" +
                    "• Intercept Node: ${alert.affectedDomainOrNode}\n" +
                    "• Detected Payload: ${alert.detectedPayloadSnippet}\n" +
                    "• Applied Proof: ${alert.cryptographicFingerprint}"
                )
                .setSummaryText(if (isCritical) "CRITICAL TIER-0 ANOMALY" else "HIGH-RISK SENTINEL ALERT")

            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("⚠️ ${alert.title}")
                .setContentText("Risk: $riskPercentage% • ${alert.description}")
                .setStyle(bigTextStyle)
                .setPriority(if (isCritical) NotificationCompat.PRIORITY_MAX else NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setColor(if (isCritical) 0xFFFF2A55.toInt() else 0xFFFFB703.toInt())
                .setSound(defaultSoundUri)
                .setVibrate(longArrayOf(0, 150, 80, 150, 100, 300))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

            val notificationId = BASE_NOTIFICATION_ID + (alert.id.hashCode() % 1000)
            NotificationManagerCompat.from(context).notify(notificationId, notificationBuilder.build())
        } catch (e: SecurityException) {
            // Permission not granted yet on Android 13+; in-app HUD visual alerts will render gracefully
        } catch (e: Exception) {
            // Graceful fallback for non-blocking execution
        }
    }

    fun dismissNotification(alertId: String) {
        try {
            val notificationId = BASE_NOTIFICATION_ID + (alertId.hashCode() % 1000)
            notificationManager.cancel(notificationId)
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun cancelAll() {
        try {
            notificationManager.cancelAll()
        } catch (e: Exception) {
            // Ignore
        }
    }
}
