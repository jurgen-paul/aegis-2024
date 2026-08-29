package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RadarTarget
import com.example.model.RadarThreatLevel
import com.example.model.TargetClassification
import com.example.ui.animation.PhotonicSignalPulseIndicator
import com.example.ui.animation.QuantumVolumetricButton
import com.example.ui.theme.*
import com.example.viewmodel.AgisViewModel
import java.util.*
import kotlin.math.*

/**
 * 360-degree Interactive Quantum Radar Track & Trace Scope with
 * rotating phosphorescent beam, real-time blip trajectory vectors,
 * azimuth polar grid, and touch-to-lock tracking capability.
 */
@Composable
fun RadarTrackAndTraceCard(
    viewModel: AgisViewModel,
    modifier: Modifier = Modifier
) {
    val targets by viewModel.radarTargets.collectAsState()
    val lockedTargetId by viewModel.lockedTargetId.collectAsState()
    val rangeZoom by viewModel.radarRangeZoomMeters.collectAsState()
    val isScanning by viewModel.isRadarScanning.collectAsState()

    val lockedTarget = targets.find { it.id == lockedTargetId }

    // Continuous 360 rotating radar sweep animation
    val infiniteTransition = rememberInfiniteTransition(label = "RadarSweep")
    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isScanning) 3500 else 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sweepAngle"
    )

    val pulsePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulsePhase"
    )

    QuantumGlassCard(
        borderColor = if (lockedTarget?.threatLevel == RadarThreatLevel.HOSTILE) ContainmentCrimson.copy(alpha = 0.8f) else PhotonicCyan.copy(alpha = 0.4f),
        backgroundColor = SpaceCobaltSurface,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Radar Header & Status
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
                        signalColor = if (lockedTarget?.threatLevel == RadarThreatLevel.HOSTILE) ContainmentCrimson else OperationalEmerald,
                        size = 12.dp
                    )
                    Column {
                        Text(
                            text = "360° QUANTUM RADAR TRACK & TRACE",
                            style = MaterialTheme.typography.labelSmall,
                            color = PhotonicCyanLight,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Active Perimeter Sonar & Kinematic Vectoring",
                            style = MaterialTheme.typography.titleSmall,
                            color = AmbientWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    PhotonicBadge(
                        text = "${rangeZoom.toInt()}M RANGE",
                        signalColor = PhotonicCyan,
                        modifier = Modifier.clip(RoundedCornerShape(6.dp))
                    )
                    PhotonicBadge(
                        text = "${targets.size} CONTACTS",
                        signalColor = if (targets.any { it.threatLevel == RadarThreatLevel.HOSTILE }) ContainmentCrimson else OperationalEmerald
                    )
                }
            }

            // Interactive Radar Scope Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SpaceCobaltDark)
                    .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(targets, rangeZoom) {
                            detectTapGestures { tapOffset ->
                                val center = Offset(size.width / 2f, size.height / 2f)
                                val radius = (min(size.width, size.height) / 2f) * 0.88f
                                val dx = tapOffset.x - center.x
                                val dy = tapOffset.y - center.y
                                val tapDistPx = sqrt(dx * dx + dy * dy)
                                val tapRangeM = (tapDistPx / radius) * rangeZoom
                                val tapBearingDeg = (Math.toDegrees(atan2(dx.toDouble(), -dy.toDouble())).toFloat() + 360f) % 360f

                                // Find closest target within threshold
                                var closestTarget: RadarTarget? = null
                                var minDelta = Float.MAX_VALUE

                                targets.forEach { tgt ->
                                    val tgtAngleRad = Math.toRadians((tgt.bearingDegrees - 90.0))
                                    val tgtDistPx = (tgt.rangeMeters / rangeZoom) * radius
                                    val tgtX = center.x + (cos(tgtAngleRad) * tgtDistPx).toFloat()
                                    val tgtY = center.y + (sin(tgtAngleRad) * tgtDistPx).toFloat()
                                    val distToTap = sqrt((tapOffset.x - tgtX).pow(2) + (tapOffset.y - tgtY).pow(2))
                                    if (distToTap < 32.dp.toPx() && distToTap < minDelta) {
                                        minDelta = distToTap
                                        closestTarget = tgt
                                    }
                                }

                                if (closestTarget != null) {
                                    viewModel.lockRadarTarget(closestTarget!!.id)
                                } else {
                                    viewModel.unlockRadarTarget()
                                }
                            }
                        }
                ) {
                    drawRadarScope(
                        targets = targets,
                        lockedTargetId = lockedTargetId,
                        sweepAngle = sweepAngle,
                        pulsePhase = pulsePhase,
                        rangeZoom = rangeZoom,
                        isScanning = isScanning
                    )
                }

                // Central Node Badge
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(PhotonicCyan)
                        .border(2.dp, AmbientWhite, CircleShape)
                )
            }

            // Radar Scope Controls & Zoom Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(100f to "100m", 250f to "250m", 500f to "500m", 1000f to "1km").forEach { (r, label) ->
                    val isSelected = rangeZoom == r
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) PhotonicCyan.copy(alpha = 0.25f) else SpaceCobaltGlassElevated)
                            .border(1.dp, if (isSelected) PhotonicCyan else SpaceCobaltGlassBorder, RoundedCornerShape(8.dp))
                            .clickable { viewModel.cycleRadarRange() }
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) PhotonicCyan else AmbientWhiteMuted,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            // Live Target Dossier Bar when Locked
            if (lockedTarget != null) {
                LockedTargetDossierCard(
                    target = lockedTarget,
                    onUnlock = { viewModel.unlockRadarTarget() },
                    onRunAttestation = {
                        lockedTarget.matchedSubjectId?.let {
                            viewModel.runComprehensiveMultiModalAttestation(it)
                        }
                    }
                )
            }

            // Action Dispatchers
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuantumVolumetricButton(
                    text = if (isScanning) "⏸️ Passive Sweep" else "📡 Active Radar",
                    onClick = { viewModel.toggleRadarScanning() },
                    primaryColor = PhotonicCyan,
                    secondaryColor = OperationalEmerald,
                    icon = Icons.Default.Radar,
                    modifier = Modifier.weight(1f)
                )

                QuantumVolumetricButton(
                    text = "🎯 Inject Contact",
                    onClick = { viewModel.injectSimulatedRadarTarget() },
                    primaryColor = SolarAmber,
                    secondaryColor = ContainmentCrimson,
                    icon = Icons.Default.AddLocation,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * Locked Target detail card with kinematic vectors and 1-tap attestation
 */
@Composable
private fun LockedTargetDossierCard(
    target: RadarTarget,
    onUnlock: () -> Unit,
    onRunAttestation: () -> Unit
) {
    val targetColor = when (target.threatLevel) {
        RadarThreatLevel.FRIENDLY -> OperationalEmerald
        RadarThreatLevel.NEUTRAL -> PhotonicCyan
        RadarThreatLevel.HOSTILE -> ContainmentCrimson
        RadarThreatLevel.UNKNOWN -> SolarAmber
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(SpaceCobaltGlassElevated)
            .border(1.5.dp, targetColor.copy(alpha = 0.7f), RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.GpsFixed,
                        contentDescription = "Lock-on",
                        tint = targetColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Column {
                        Text(
                            text = "TRACE-LOCKED: ${target.codeName}",
                            style = MaterialTheme.typography.labelMedium,
                            color = targetColor,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${target.classification.label} • ${target.threatLevel.label}",
                            style = MaterialTheme.typography.labelSmall,
                            color = AmbientWhiteMuted,
                            fontSize = 10.sp
                        )
                    }
                }

                IconButton(
                    onClick = onUnlock,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Release Lock",
                        tint = AmbientWhiteMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Kinematics Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                KinematicTile(label = "RANGE", value = "${target.rangeMeters.toInt()} m", modifier = Modifier.weight(1f))
                KinematicTile(label = "AZIMUTH", value = "${target.bearingDegrees.toInt()}°", modifier = Modifier.weight(1f))
                KinematicTile(label = "VELOCITY", value = "${target.velocityKmh.toInt()} km/h", modifier = Modifier.weight(1f))
                KinematicTile(label = "HEADING", value = "${target.headingDegrees.toInt()}°", modifier = Modifier.weight(1f))
            }

            if (target.matchedSubjectId != null) {
                Button(
                    onClick = onRunAttestation,
                    colors = ButtonDefaults.buttonColors(containerColor = targetColor.copy(alpha = 0.25f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, targetColor),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(36.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Icon(imageVector = Icons.Default.Fingerprint, contentDescription = null, tint = targetColor, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Trigger 5-Vector Multi-Modal Biometric Lock",
                        style = MaterialTheme.typography.labelSmall,
                        color = AmbientWhite,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun KinematicTile(label: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(SpaceCobaltDark)
            .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(6.dp))
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = AmbientWhiteMuted, fontSize = 8.sp)
            Text(text = value, style = MaterialTheme.typography.bodySmall, color = AmbientWhite, fontWeight = FontWeight.Bold)
        }
    }
}

/**
 * Custom Canvas drawing routine for high-precision Radar Scope
 */
private fun DrawScope.drawRadarScope(
    targets: List<RadarTarget>,
    lockedTargetId: String?,
    sweepAngle: Float,
    pulsePhase: Float,
    rangeZoom: Float,
    isScanning: Boolean
) {
    val w = size.width
    val h = size.height
    val center = Offset(w / 2f, h / 2f)
    val maxRadius = (min(w, h) / 2f) * 0.88f

    // 1. Polar Range Rings (4 concentric circles)
    val ringCount = 4
    for (i in 1..ringCount) {
        val r = (maxRadius / ringCount) * i
        drawCircle(
            color = SpaceCobaltGlassBorder.copy(alpha = 0.5f),
            radius = r,
            center = center,
            style = Stroke(width = 1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f))
        )
    }

    // 2. Crosshairs & Azimuth Cardinal Axes (0°, 90°, 180°, 270°)
    drawLine(
        color = SpaceCobaltGlassBorder.copy(alpha = 0.6f),
        start = Offset(center.x - maxRadius, center.y),
        end = Offset(center.x + maxRadius, center.y),
        strokeWidth = 1.dp.toPx()
    )
    drawLine(
        color = SpaceCobaltGlassBorder.copy(alpha = 0.6f),
        start = Offset(center.x, center.y - maxRadius),
        end = Offset(center.x, center.y + maxRadius),
        strokeWidth = 1.dp.toPx()
    )

    // Diagonal tick marks (45°, 135°, 225°, 315°)
    val diagLen = maxRadius * 0.95f
    val angles = listOf(45.0, 135.0, 225.0, 315.0)
    angles.forEach { deg ->
        val rad = Math.toRadians(deg)
        val endX = center.x + (cos(rad) * diagLen).toFloat()
        val endY = center.y + (sin(rad) * diagLen).toFloat()
        drawLine(
            color = SpaceCobaltGlassBorder.copy(alpha = 0.3f),
            start = center,
            end = Offset(endX, endY),
            strokeWidth = 0.8.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(2f, 4f), 0f)
        )
    }

    // 3. Rotating Phosphorescent Sweep Beam (Pie sector gradient)
    if (isScanning) {
        rotate(sweepAngle, pivot = center) {
            val sweepBrush = Brush.sweepGradient(
                0.0f to PhotonicCyan.copy(alpha = 0.45f),
                0.12f to PhotonicCyan.copy(alpha = 0.08f),
                0.20f to Color.Transparent,
                1.0f to Color.Transparent,
                center = center
            )
            drawCircle(
                brush = sweepBrush,
                radius = maxRadius,
                center = center
            )

            // Leading photon needle
            drawLine(
                color = PhotonicCyan,
                start = center,
                end = Offset(center.x + maxRadius, center.y),
                strokeWidth = 2.dp.toPx()
            )
        }
    }

    // 4. Draw Radar Target Blips
    targets.forEach { target ->
        val isLocked = target.id == lockedTargetId
        val targetColor = when (target.threatLevel) {
            RadarThreatLevel.FRIENDLY -> OperationalEmerald
            RadarThreatLevel.NEUTRAL -> PhotonicCyan
            RadarThreatLevel.HOSTILE -> ContainmentCrimson
            RadarThreatLevel.UNKNOWN -> SolarAmber
        }

        // Convert Polar (rangeMeters, bearingDegrees) to Cartesian Pixel Position
        // Bearing 0° = North (up), 90° = East (right), 180° = South (down), 270° = West (left)
        val bearingRad = Math.toRadians((target.bearingDegrees - 90.0))
        val distPx = (target.rangeMeters / rangeZoom).coerceIn(0f, 1f) * maxRadius
        val targetX = center.x + (cos(bearingRad) * distPx).toFloat()
        val targetY = center.y + (sin(bearingRad) * distPx).toFloat()
        val targetCenter = Offset(targetX, targetY)

        // Trajectory History Dots (phosphor decay trail)
        target.trajectoryHistory.forEachIndexed { idx, (histRange, histBearing) ->
            val histRad = Math.toRadians((histBearing - 90.0))
            val histDistPx = (histRange / rangeZoom).coerceIn(0f, 1f) * maxRadius
            val hX = center.x + (cos(histRad) * histDistPx).toFloat()
            val hY = center.y + (sin(histRad) * histDistPx).toFloat()
            val alpha = (idx + 1f) / (target.trajectoryHistory.size + 1f) * 0.4f
            drawCircle(
                color = targetColor.copy(alpha = alpha),
                radius = 2.5.dp.toPx(),
                center = Offset(hX, hY)
            )
        }

        // Velocity Vector Line
        val headingRad = Math.toRadians((target.headingDegrees - 90.0))
        val velLen = (target.velocityKmh / 50f).coerceIn(0.2f, 1.2f) * 20.dp.toPx()
        val velEndX = targetX + (cos(headingRad) * velLen).toFloat()
        val velEndY = targetY + (sin(headingRad) * velLen).toFloat()
        drawLine(
            color = targetColor.copy(alpha = 0.8f),
            start = targetCenter,
            end = Offset(velEndX, velEndY),
            strokeWidth = 1.5.dp.toPx()
        )

        // Target Core Blip
        drawCircle(
            color = targetColor.copy(alpha = 0.35f),
            radius = 7.dp.toPx() * (if (isLocked) 1f + pulsePhase * 0.5f else 1f),
            center = targetCenter
        )
        drawCircle(
            color = targetColor,
            radius = 3.5.dp.toPx(),
            center = targetCenter
        )

        // Lock Reticle Frame if locked
        if (isLocked) {
            val boxSize = 22.dp.toPx()
            drawRect(
                color = targetColor,
                topLeft = Offset(targetX - boxSize / 2, targetY - boxSize / 2),
                size = Size(boxSize, boxSize),
                style = Stroke(width = 1.5.dp.toPx())
            )
            // Lock corner brackets
            val bracketLen = 5.dp.toPx()
            drawLine(
                color = targetColor,
                start = Offset(targetX - boxSize / 2, targetY - boxSize / 2),
                end = Offset(targetX - boxSize / 2 + bracketLen, targetY - boxSize / 2),
                strokeWidth = 2.dp.toPx()
            )
            drawLine(
                color = targetColor,
                start = Offset(targetX + boxSize / 2 - bracketLen, targetY - boxSize / 2),
                end = Offset(targetX + boxSize / 2, targetY - boxSize / 2),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
}
