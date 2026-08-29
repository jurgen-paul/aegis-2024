package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TelemetryAnomalyAlert
import com.example.model.TelemetryAnomalyType
import com.example.model.ThreatSeverity
import com.example.ui.animation.PhotonicSignalPulseIndicator
import com.example.ui.animation.QuantumVolumetricButton
import com.example.ui.theme.*
import com.example.viewmodel.AgisViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Custom Visual Alert Center for High-Risk Telemetry Anomalies.
 * Renders pulsating Heads-Up HUD warning banners, interactive containment quick-actions,
 * payload inspection diffs, and real-time sanitization anomaly test controls.
 */
@Composable
fun TelemetryAnomalyAlertCenter(
    viewModel: AgisViewModel,
    modifier: Modifier = Modifier
) {
    val latestAnomaly by viewModel.latestHighRiskAnomaly.collectAsState()
    val activeAlerts by viewModel.activeAnomalyAlerts.collectAsState()
    val anomalyHistory by viewModel.telemetryAnomalyHistory.collectAsState()

    var isHistoryExpanded by remember { mutableStateOf(false) }
    var isSimulationExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // High-Risk Anomaly Heads-Up Alert (If Active)
        AnimatedVisibility(
            visible = latestAnomaly != null,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            latestAnomaly?.let { alert ->
                HighRiskAnomalyAlertCard(
                    alert = alert,
                    onMitigate = { action -> viewModel.mitigateAnomaly(alert.id, action) },
                    onDismiss = { viewModel.dismissLatestAnomalyBanner() }
                )
            }
        }

        // Real-Time Anomaly Sentinel KPI & Test Console Card
        QuantumGlassCard(
            borderColor = if (latestAnomaly != null) ContainmentCrimson else PhotonicCyan.copy(alpha = 0.4f),
            backgroundColor = SpaceCobaltSurface
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        PhotonicSignalPulseIndicator(
                            signalColor = if (latestAnomaly != null) ContainmentCrimson else OperationalEmerald,
                            size = 12.dp,
                            pulseSpeedMs = if (latestAnomaly != null) 700 else 1800
                        )
                        Column {
                            Text(
                                text = "REAL-TIME TELEMETRY SANITIZATION SENTINEL",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (latestAnomaly != null) ContainmentCrimson else PhotonicCyanLight,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "High-Risk Anomaly Detection & Notification Engine",
                                style = MaterialTheme.typography.titleSmall,
                                color = AmbientWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    PhotonicBadge(
                        text = if (activeAlerts.isNotEmpty()) "${activeAlerts.size} ANOMALIES" else "STREAM CLEAN",
                        signalColor = if (activeAlerts.isNotEmpty()) ContainmentCrimson else OperationalEmerald,
                        icon = if (activeAlerts.isNotEmpty()) Icons.Default.Warning else Icons.Default.CheckCircle
                    )
                }

                Text(
                    text = "Live telemetry ingestion stream is actively monitored for unmasked PII, prompt injections, differential privacy budget collapse (ε < 0.1), and unauthorized memory dumps.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AmbientWhiteMuted,
                    fontSize = 11.sp
                )

                // Expandable Simulation Trigger Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { isSimulationExpanded = !isSimulationExpanded },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isSimulationExpanded) Icons.Default.ExpandLess else Icons.Default.Science,
                                contentDescription = "Toggle Test Simulator",
                                tint = PhotonicCyanLight,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isSimulationExpanded) "Hide Anomaly Test Simulator" else "🧪 Test Sanitization Anomaly Alerts",
                                style = MaterialTheme.typography.labelSmall,
                                color = PhotonicCyanLight,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    if (anomalyHistory.isNotEmpty()) {
                        TextButton(
                            onClick = { isHistoryExpanded = !isHistoryExpanded },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isHistoryExpanded) Icons.Default.ExpandLess else Icons.Default.History,
                                    contentDescription = "Toggle History",
                                    tint = AmbientWhiteMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "History (${anomalyHistory.size})",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AmbientWhiteMuted
                                )
                            }
                        }
                    }
                }

                // Simulation Buttons
                AnimatedVisibility(
                    visible = isSimulationExpanded,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SpaceCobaltGlassElevated)
                            .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "TRIGGER SANITIZATION ANOMALY TEST SCENARIOS:",
                            style = MaterialTheme.typography.labelSmall,
                            color = PhotonicAmber,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            QuantumVolumetricButton(
                                text = "PII Leak",
                                icon = Icons.Default.PersonOff,
                                glowColor = ContainmentCrimson,
                                onClick = { viewModel.triggerSimulatedTelemetryAnomaly(TelemetryAnomalyType.UNMASKED_PII_LEAK) },
                                modifier = Modifier.weight(1f)
                            )
                            QuantumVolumetricButton(
                                text = "Prompt Inject",
                                icon = Icons.Default.Terminal,
                                glowColor = ContainmentCrimson,
                                onClick = { viewModel.triggerSimulatedTelemetryAnomaly(TelemetryAnomalyType.PROMPT_INJECTION_PAYLOAD) },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            QuantumVolumetricButton(
                                text = "ε-Collapse",
                                icon = Icons.Default.BlurOn,
                                glowColor = PhotonicAmber,
                                onClick = { viewModel.triggerSimulatedTelemetryAnomaly(TelemetryAnomalyType.DIFFERENTIAL_PRIVACY_VIOLATION) },
                                modifier = Modifier.weight(1f)
                            )
                            QuantumVolumetricButton(
                                text = "Memory Dump",
                                icon = Icons.Default.Memory,
                                glowColor = QuantumVioletLight,
                                onClick = { viewModel.triggerSimulatedTelemetryAnomaly(TelemetryAnomalyType.MEMORY_REGISTER_EXFIL) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Anomaly History Drawer
                AnimatedVisibility(
                    visible = isHistoryExpanded,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SpaceCobaltGlass)
                            .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "INTERCEPTED ANOMALY AUDIT TRAIL",
                                style = MaterialTheme.typography.labelSmall,
                                color = PhotonicCyanLight,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Clear",
                                style = MaterialTheme.typography.labelSmall,
                                color = ContainmentCrimsonLight,
                                modifier = Modifier.clickable { viewModel.clearAllAnomalies() }
                            )
                        }

                        anomalyHistory.take(5).forEach { item ->
                            val timeStr = SimpleDateFormat("HH:mm:ss", Locale.US).format(Date(item.timestamp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SpaceCobaltSurface)
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = item.anomalyType.shortCode,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (item.severity == ThreatSeverity.CRITICAL) ContainmentCrimson else PhotonicAmber,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "@ $timeStr",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AmbientWhiteSubtle,
                                            fontSize = 10.sp
                                        )
                                    }
                                    Text(
                                        text = item.title,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AmbientWhite,
                                        fontSize = 11.sp,
                                        maxLines = 1
                                    )
                                }

                                PhotonicBadge(
                                    text = if (item.isMitigated) "SCRUBBED" else "QUARANTINED",
                                    signalColor = if (item.isMitigated) OperationalEmerald else ContainmentCrimson,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * High-Impact Custom Visual Alert Card for High-Risk Telemetry Anomalies.
 */
@Composable
fun HighRiskAnomalyAlertCard(
    alert: TelemetryAnomalyAlert,
    onMitigate: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_anomaly")
    val borderAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "border_alpha"
    )

    val isCritical = alert.severity == ThreatSeverity.CRITICAL
    val primaryColor = if (isCritical) ContainmentCrimson else PhotonicAmber
    val secondaryColor = if (isCritical) ContainmentCrimsonDark else PhotonicAmberDark
    val timeFormatted = SimpleDateFormat("HH:mm:ss", Locale.US).format(Date(alert.timestamp))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        secondaryColor.copy(alpha = 0.55f),
                        SpaceCobaltDark.copy(alpha = 0.95f)
                    )
                )
            )
            .border(
                width = 2.dp,
                brush = Brush.horizontalGradient(
                    listOf(
                        primaryColor.copy(alpha = borderAlpha),
                        primaryColor.copy(alpha = 0.4f),
                        primaryColor.copy(alpha = borderAlpha)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(primaryColor.copy(alpha = 0.25f))
                            .border(1.5.dp, primaryColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = "High Risk Anomaly",
                            tint = primaryColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "HIGH-RISK TELEMETRY ANOMALY",
                                style = MaterialTheme.typography.labelSmall,
                                color = primaryColor,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "@ $timeFormatted",
                                style = MaterialTheme.typography.labelSmall,
                                color = AmbientWhiteMuted,
                                fontSize = 10.sp
                            )
                        }
                        Text(
                            text = alert.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = AmbientWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss Alert",
                        tint = AmbientWhiteMuted,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Risk Assessment Metric Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(SpaceCobaltCard)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "RISK SCORE:",
                        style = MaterialTheme.typography.labelSmall,
                        color = AmbientWhiteMuted,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${(alert.riskScore * 100).toInt()}% ${alert.severity.label}",
                        style = MaterialTheme.typography.labelSmall,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "VECTOR:",
                        style = MaterialTheme.typography.labelSmall,
                        color = AmbientWhiteMuted,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = alert.anomalyType.shortCode,
                        style = MaterialTheme.typography.labelSmall,
                        color = PhotonicCyanLight,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Description
            Text(
                text = alert.description,
                style = MaterialTheme.typography.bodySmall,
                color = AmbientWhite,
                fontSize = 12.sp
            )

            // Detected Payload & Quarantine Snippet Box
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF070B16))
                    .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "DETECTED TAINTED SNIPPET:",
                        style = MaterialTheme.typography.labelSmall,
                        color = primaryColor,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = alert.affectedDomainOrNode,
                        style = MaterialTheme.typography.labelSmall,
                        color = AmbientWhiteSubtle,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Text(
                    text = alert.detectedPayloadSnippet,
                    style = MaterialTheme.typography.bodySmall,
                    color = primaryColor.copy(alpha = 0.95f),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    maxLines = 2
                )

                Divider(
                    color = SpaceCobaltGlassBorder.copy(alpha = 0.5f),
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "SANITIZER ENFORCEMENT PROOF:",
                        style = MaterialTheme.typography.labelSmall,
                        color = OperationalEmerald,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = alert.cryptographicFingerprint,
                        style = MaterialTheme.typography.labelSmall,
                        color = PhotonicCyanLight,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Text(
                    text = alert.redactionRuleApplied,
                    style = MaterialTheme.typography.bodySmall,
                    color = OperationalEmeraldLight,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    maxLines = 1
                )
            }

            // Quick Containment Mitigation Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuantumVolumetricButton(
                    text = "Zero-Trust Isolate",
                    icon = Icons.Default.Shield,
                    glowColor = ContainmentCrimson,
                    onClick = { onMitigate("ZERO_TRUST_ISOLATION") },
                    modifier = Modifier.weight(1.2f)
                )

                QuantumVolumetricButton(
                    text = "Flush Buffer (ε=0.5)",
                    icon = Icons.Default.CleaningServices,
                    glowColor = OperationalEmerald,
                    onClick = { onMitigate("FLUSH_BUFFER_RE_SCRUB") },
                    modifier = Modifier.weight(1.2f)
                )

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .height(44.dp)
                        .weight(0.8f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SpaceCobaltCard,
                        contentColor = AmbientWhiteMuted
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Dismiss",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}
