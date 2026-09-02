package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AgentStatus
import com.example.model.BiometricState
import com.example.model.SubAgentThread
import com.example.model.ThreatSeverity
import com.example.ui.animation.*
import com.example.ui.theme.*

@Composable
fun QuantumGlassCard(
    modifier: Modifier = Modifier,
    borderColor: Color = SpaceCobaltGlassBorder,
    backgroundColor: Color = SpaceCobaltGlass,
    elevation: Dp = 6.dp,
    shapeRadius: Dp = 16.dp,
    enableVolumetricHover: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardShape = RoundedCornerShape(shapeRadius)
    val clickableModifier = if (onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else Modifier

    val volumetricModifier = if (enableVolumetricHover) {
        Modifier.volumetricQuantumGlass(
            shapeRadius = shapeRadius,
            elevation = elevation,
            primarySignalColor = borderColor,
            secondarySignalColor = OperationalEmerald,
            glassBackground = backgroundColor,
            maxTiltAngle = 8f
        )
    } else {
        Modifier
            .shadow(elevation, cardShape, ambientColor = borderColor.copy(alpha = 0.25f), spotColor = borderColor.copy(alpha = 0.35f))
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        borderColor.copy(alpha = 0.75f),
                        borderColor.copy(alpha = 0.15f),
                        borderColor.copy(alpha = 0.45f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset.Infinite
                ),
                shape = cardShape
            )
    }

    Box(
        modifier = modifier
            .then(volumetricModifier)
            .clip(cardShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundColor.copy(alpha = 0.95f),
                        backgroundColor.copy(alpha = 0.70f)
                    )
                )
            )
            .then(clickableModifier)
            .padding(16.dp)
    ) {
        Column {
            content()
        }
    }
}

@Composable
fun BiometricHeader(
    biometrics: BiometricState,
    threatSeverity: ThreatSeverity,
    modifier: Modifier = Modifier
) {
    QuantumGlassCard(
        modifier = modifier.fillMaxWidth(),
        borderColor = if (threatSeverity == ThreatSeverity.CRITICAL) ContainmentCrimson else SpaceCobaltGlassBorder,
        backgroundColor = SpaceCobaltCard
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (threatSeverity == ThreatSeverity.CRITICAL) ContainmentCrimson else OperationalEmerald
                            )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "AGIS-2045 QUANTUM NODE",
                        style = MaterialTheme.typography.labelMedium,
                        color = PhotonicCyan,
                        letterSpacing = 1.2.sp
                    )
                }
                Text(
                    text = "BIOMORPHIC ZERO-TRUST CORE",
                    style = MaterialTheme.typography.titleMedium,
                    color = AmbientWhite,
                    fontWeight = FontWeight.Bold
                )
            }

            // Enclave Isolation Pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(SpaceCobaltGlassElevated)
                    .border(1.dp, PhotonicCyan.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Hardware Enclave Locked",
                        tint = PhotonicCyan,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "ENCLAVE DUAL-ISOLATED",
                        style = MaterialTheme.typography.labelSmall,
                        color = PhotonicCyanLight
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Biometrics Real-time Telemetry Strip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Neural Pulse Waveform
            Column(modifier = Modifier.weight(1.3f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Neural Pulse",
                        tint = if (threatSeverity == ThreatSeverity.CRITICAL) ContainmentCrimson else PhotonicCyan,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${biometrics.neuralPulseBpm} BPM",
                        style = MaterialTheme.typography.labelLarge,
                        color = AmbientWhite,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "NEURAL RES",
                        style = MaterialTheme.typography.labelSmall,
                        color = AmbientWhiteSubtle
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                LiveWaveformCanvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(26.dp),
                    waveColor = if (threatSeverity == ThreatSeverity.CRITICAL) ContainmentCrimson else PhotonicCyan
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Galvanic Stress
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "GALVANIC",
                    style = MaterialTheme.typography.labelSmall,
                    color = AmbientWhiteSubtle
                )
                Text(
                    text = "${biometrics.galvanicConductance} μS",
                    style = MaterialTheme.typography.labelLarge,
                    color = OperationalEmerald,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "SYNC: ${(biometrics.neuralSyncRatio * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = AmbientWhiteMuted
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Entropy Rate
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "ENTROPY",
                    style = MaterialTheme.typography.labelSmall,
                    color = AmbientWhiteSubtle
                )
                Text(
                    text = "${biometrics.entropyRateKbps}k",
                    style = MaterialTheme.typography.labelLarge,
                    color = QuantumVioletLight,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "PQ-RANDOM",
                    style = MaterialTheme.typography.labelSmall,
                    color = AmbientWhiteMuted
                )
            }
        }
    }
}

@Composable
fun LiveWaveformCanvas(
    modifier: Modifier = Modifier,
    waveColor: Color = PhotonicCyan
) {
    val infiniteTransition = rememberInfiniteTransition(label = "WaveAnimation")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "WavePhase"
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val midY = height / 2f

        val path = Path()
        val points = 30
        for (i in 0..points) {
            val x = (width / points) * i
            val rad = Math.toRadians((i * 24.0 + phase)).toFloat()
            // Synthetic heartbeat spike pattern
            val spikeFactor = if (i % 8 == 4) 1.8f else if (i % 8 == 5) -1.4f else 0.4f
            val y = midY + kotlin.math.sin(rad) * (height / 3.2f) * spikeFactor
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }

        drawPath(
            path = path,
            color = waveColor,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Composable
fun PhotonicBadge(
    text: String,
    signalColor: Color,
    icon: ImageVector? = null,
    enablePulse: Boolean = false,
    fontSize: androidx.compose.ui.unit.TextUnit? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(signalColor.copy(alpha = 0.15f))
            .border(1.dp, signalColor.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (enablePulse) {
                PhotonicSignalPulseIndicator(
                    signalColor = signalColor,
                    size = 6.dp,
                    pulseSpeedMs = 1500
                )
                Spacer(modifier = Modifier.width(6.dp))
            } else if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = signalColor,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = text,
                style = if (fontSize != null) MaterialTheme.typography.labelSmall.copy(fontSize = fontSize) else MaterialTheme.typography.labelSmall,
                color = signalColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun SubAgentThreadCard(
    agent: SubAgentThread,
    modifier: Modifier = Modifier
) {
    QuantumGlassCard(
        modifier = modifier.fillMaxWidth(),
        borderColor = when (agent.status) {
            AgentStatus.ACTIVE -> PhotonicCyan.copy(alpha = 0.3f)
            AgentStatus.SECURING -> OperationalEmerald.copy(alpha = 0.4f)
            AgentStatus.ISOLATED -> QuantumViolet.copy(alpha = 0.4f)
            AgentStatus.SCANNING -> SolarAmber.copy(alpha = 0.4f)
        },
        backgroundColor = SpaceCobaltGlassElevated
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = when (agent.id) {
                        "AGENT-ALPHA" -> Icons.Default.Shield
                        "AGENT-BETA" -> Icons.Default.FilterAlt
                        "AGENT-GAMMA" -> Icons.Default.Key
                        else -> Icons.Default.Verified
                    },
                    contentDescription = agent.name,
                    tint = PhotonicCyan,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = agent.id,
                        style = MaterialTheme.typography.labelLarge,
                        color = PhotonicCyanLight,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = agent.name,
                        style = MaterialTheme.typography.bodySmall,
                        color = AmbientWhite
                    )
                }
            }

            PhotonicBadge(
                text = agent.status.label,
                signalColor = when (agent.status) {
                    AgentStatus.ACTIVE -> OperationalEmerald
                    AgentStatus.SECURING -> PhotonicCyan
                    AgentStatus.ISOLATED -> QuantumViolet
                    AgentStatus.SCANNING -> SolarAmber
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = agent.role,
            style = MaterialTheme.typography.bodySmall,
            color = AmbientWhiteMuted
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Neural Load & Metrics
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "NEURAL LOAD",
                        style = MaterialTheme.typography.labelSmall,
                        color = AmbientWhiteSubtle
                    )
                    Text(
                        text = "${(agent.neuralLoad * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = PhotonicCyanLight
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { agent.neuralLoad },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = if (agent.neuralLoad > 0.85f) ContainmentCrimson else PhotonicCyan,
                    trackColor = SpaceCobaltSurface
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "LATENCY: ${agent.latencyMs}ms",
                    style = MaterialTheme.typography.labelSmall,
                    color = OperationalEmerald
                )
                Text(
                    text = "TASKS: ${agent.handledTasks}",
                    style = MaterialTheme.typography.labelSmall,
                    color = AmbientWhiteMuted
                )
            }
        }
    }
}
