package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.IntentRiskLevel
import com.example.model.NeuralIntentPattern
import com.example.model.NeuralTopologyNode
import com.example.ui.animation.PhotonicSignalPulseIndicator
import com.example.ui.animation.QuantumVolumetricButton
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

/**
 * Neural Intent Routing Diagnostic Dashboard
 * Visualizes real-time user cognitive intent patterns, topology node connections,
 * and synchronic cryptographic routing using the Emerald & Cyan photonic color palette.
 */
@Composable
fun NeuralIntentRoutingDashboard(
    intentStream: List<NeuralIntentPattern>,
    topologyNodes: List<NeuralTopologyNode>,
    selectedFilter: String,
    onSelectFilter: (String) -> Unit,
    onInjectIntent: (String, String, IntentRiskLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredIntents = remember(intentStream, selectedFilter) {
        if (selectedFilter == "ALL") intentStream
        else intentStream.filter {
            it.riskLevel.name.equals(selectedFilter, ignoreCase = true) ||
            it.intentType.contains(selectedFilter, ignoreCase = true)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header with Pulse Status
        QuantumGlassCard(
            borderColor = PhotonicCyan.copy(alpha = 0.5f),
            backgroundColor = SpaceCobaltGlassElevated
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PhotonicSignalPulseIndicator(
                        signalColor = OperationalEmerald,
                        size = 10.dp,
                        pulseSpeedMs = 1000
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "NEURAL INTENT ROUTING TOPOLOGY",
                            style = MaterialTheme.typography.labelSmall,
                            color = PhotonicCyan,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Real-time Cognitive Intent Stream",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AmbientWhite
                        )
                    }
                }

                PhotonicBadge(
                    text = "${intentStream.size} INTENTS CAPTURED",
                    signalColor = PhotonicCyan,
                    icon = Icons.Default.Hub,
                    enablePulse = true
                )
            }
        }

        // Interactive Neural Node Connection Topology Canvas
        QuantumGlassCard(
            borderColor = OperationalEmerald.copy(alpha = 0.45f),
            backgroundColor = SpaceCobaltGlass
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SYNCHRONIC PHOTONIC NODE GRAPH",
                    style = MaterialTheme.typography.labelSmall,
                    color = OperationalEmeraldLight,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Latency: ~3.2ms • Quantum Coherent",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = TextDimmed
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            NeuralTopologyCanvas(
                nodes = topologyNodes,
                activeIntents = intentStream.take(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Quick Intent Injection Bar
            Text(
                text = "DISPATCH SYNTHETIC INTENT PROBE:",
                style = MaterialTheme.typography.labelSmall,
                color = TextDimmed,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    Triple("ENCLAVE_READ", "Gated Kyber Read", IntentRiskLevel.ELEVATED),
                    Triple("SUB_AGENT_DISPATCH", "Sub-Agent Task", IntentRiskLevel.SAFE),
                    Triple("CROSS_DOMAIN_MUTATION", "Cross-Domain Gate", IntentRiskLevel.RESTRICTED)
                ).forEach { (type, label, risk) ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(
                                when (risk) {
                                    IntentRiskLevel.SAFE -> OperationalEmeraldDark.copy(alpha = 0.4f)
                                    IntentRiskLevel.ELEVATED -> QuantumVioletDark.copy(alpha = 0.4f)
                                    IntentRiskLevel.RESTRICTED -> SolarAmberDark.copy(alpha = 0.4f)
                                    IntentRiskLevel.ISOLATED -> ContainmentCrimsonDark.copy(alpha = 0.4f)
                                }
                            )
                            .border(
                                1.dp,
                                when (risk) {
                                    IntentRiskLevel.SAFE -> OperationalEmerald.copy(alpha = 0.5f)
                                    IntentRiskLevel.ELEVATED -> QuantumVioletLight.copy(alpha = 0.5f)
                                    IntentRiskLevel.RESTRICTED -> SolarAmber.copy(alpha = 0.5f)
                                    IntentRiskLevel.ISOLATED -> ContainmentCrimson.copy(alpha = 0.5f)
                                },
                                RoundedCornerShape(6.dp)
                            )
                            .clickable { onInjectIntent(type, label, risk) }
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+ $label",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = when (risk) {
                                IntentRiskLevel.SAFE -> OperationalEmeraldLight
                                IntentRiskLevel.ELEVATED -> QuantumVioletLight
                                IntentRiskLevel.RESTRICTED -> SolarAmber
                                IntentRiskLevel.ISOLATED -> ContainmentCrimson
                            },
                            fontSize = 10.sp,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        // Filter Chips Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("ALL", "SAFE", "ELEVATED", "RESTRICTED").forEach { filterKey ->
                val isSelected = selectedFilter == filterKey
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) PhotonicCyan.copy(alpha = 0.25f) else SpaceCobaltSurface)
                        .border(
                            1.dp,
                            if (isSelected) PhotonicCyan else SpaceCobaltGlassBorder,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onSelectFilter(filterKey) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = filterKey,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) PhotonicCyan else TextDimmed
                    )
                }
            }
        }

        // Live Real-Time Stream of Detected Patterns
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            filteredIntents.forEach { pattern ->
                IntentPatternCard(pattern = pattern)
            }
        }
    }
}

/**
 * High-performance Jetpack Compose Canvas rendering the Synchronic Photonic Node Graph
 * with dynamic particles traveling along Cyan and Emerald photonic arcs.
 */
@Composable
fun NeuralTopologyCanvas(
    nodes: List<NeuralTopologyNode>,
    activeIntents: List<NeuralIntentPattern>,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "TopologyPhotonAnimation")
    val pulsePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "PulsePhase"
    )

    val energyWave by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "EnergyWave"
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SpaceCobaltDark.copy(alpha = 0.95f))
            .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(12.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Calculate node coordinates in canvas pixels
            val nodeCoords = nodes.associate { node ->
                node.nodeId to Offset(node.normalizedX * width, node.normalizedY * height)
            }

            // Draw Photonic Connection Arcs between Hub and Peripheral Nodes
            val hubCoord = nodeCoords["OPERATOR_NEURAL_HUB"] ?: Offset(width * 0.5f, height * 0.2f)

            nodeCoords.forEach { (nodeId, coord) ->
                if (nodeId != "OPERATOR_NEURAL_HUB") {
                    // Draw glowing curve arc
                    val controlX = (hubCoord.x + coord.x) / 2f + (sin(energyWave.toDouble() * Math.PI / 180.0) * 15f).toFloat()
                    val controlY = (hubCoord.y + coord.y) / 2f

                    val arcPath = Path().apply {
                        moveTo(hubCoord.x, hubCoord.y)
                        quadraticTo(controlX, controlY, coord.x, coord.y)
                    }

                    // Background soft photon glow
                    drawPath(
                        path = arcPath,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                PhotonicCyan.copy(alpha = 0.4f),
                                OperationalEmerald.copy(alpha = 0.4f)
                            ),
                            start = hubCoord,
                            end = coord
                        ),
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // Sharp core synchronic beam
                    drawPath(
                        path = arcPath,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                PhotonicCyanLight.copy(alpha = 0.8f),
                                OperationalEmeraldLight.copy(alpha = 0.8f)
                            ),
                            start = hubCoord,
                            end = coord
                        ),
                        style = Stroke(width = 1.2.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // Traveling photonic energy particle
                    val t = (pulsePhase + (nodeId.hashCode().toFloat() % 5f) * 0.2f) % 1f
                    val particleX = (1 - t) * (1 - t) * hubCoord.x + 2 * (1 - t) * t * controlX + t * t * coord.x
                    val particleY = (1 - t) * (1 - t) * hubCoord.y + 2 * (1 - t) * t * controlY + t * t * coord.y

                    drawCircle(
                        color = AmbientWhite,
                        radius = 3.5.dp.toPx(),
                        center = Offset(particleX, particleY)
                    )
                    drawCircle(
                        color = PhotonicCyan,
                        radius = 6.dp.toPx(),
                        center = Offset(particleX, particleY)
                    )
                }
            }

            // Cross-peripheral connection (Alpha to Beta, Enclave to Oracle)
            val alphaCoord = nodeCoords["AGENT_ALPHA_ROUTER"]
            val betaCoord = nodeCoords["AGENT_BETA_ISOLATOR"]
            if (alphaCoord != null && betaCoord != null) {
                drawLine(
                    brush = Brush.horizontalGradient(
                        colors = listOf(PhotonicCyan.copy(alpha = 0.35f), OperationalEmerald.copy(alpha = 0.35f))
                    ),
                    start = alphaCoord,
                    end = betaCoord,
                    strokeWidth = 1.5.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), pulsePhase * 20f)
                )
            }

            val enclaveCoord = nodeCoords["ENCLAVE_PQ_VAULT"]
            val oracleCoord = nodeCoords["ORACLE_VALIDATOR"]
            if (enclaveCoord != null && oracleCoord != null) {
                drawLine(
                    brush = Brush.horizontalGradient(
                        colors = listOf(QuantumVioletLight.copy(alpha = 0.4f), OperationalEmerald.copy(alpha = 0.4f))
                    ),
                    start = enclaveCoord,
                    end = oracleCoord,
                    strokeWidth = 1.5.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), pulsePhase * 20f)
                )
            }

            // Draw Node Circles and Pulse Halos
            nodes.forEach { node ->
                val center = nodeCoords[node.nodeId] ?: return@forEach
                val isHub = node.isPrimaryCore
                val baseColor = if (isHub) PhotonicCyan else if (node.nodeId.contains("ENCLAVE")) QuantumVioletLight else OperationalEmerald

                // Concentric Pulse Ring
                val ringRadius = (if (isHub) 20.dp else 14.dp).toPx() * (1f + (pulsePhase * 0.4f))
                drawCircle(
                    color = baseColor.copy(alpha = (1f - pulsePhase) * 0.5f),
                    radius = ringRadius,
                    center = center
                )

                // Node Base Fill
                drawCircle(
                    color = SpaceCobaltSurface,
                    radius = (if (isHub) 14.dp else 10.dp).toPx(),
                    center = center
                )

                // Node Border Glow
                drawCircle(
                    color = baseColor,
                    radius = (if (isHub) 14.dp else 10.dp).toPx(),
                    center = center,
                    style = Stroke(width = 2.dp.toPx())
                )

                // Core White Center
                drawCircle(
                    color = AmbientWhite,
                    radius = (if (isHub) 5.dp else 3.5.dp).toPx(),
                    center = center
                )
            }
        }

        // Overlay Interactive Node Labels
        nodes.forEach { node ->
            val isHub = node.isPrimaryCore
            val alignTop = node.normalizedY < 0.3f
            val alignLeft = node.normalizedX < 0.4f
            val alignRight = node.normalizedX > 0.6f

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = (node.normalizedX * 280).dp.coerceIn(8.dp, 240.dp),
                        top = (node.normalizedY * 130).dp.coerceIn(4.dp, 150.dp)
                    )
            ) {
                Column(
                    horizontalAlignment = if (alignLeft) Alignment.Start else if (alignRight) Alignment.End else Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = node.label,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isHub) PhotonicCyanLight else if (node.nodeId.contains("ENCLAVE")) QuantumVioletLight else OperationalEmeraldLight,
                        fontSize = 10.sp
                    )
                    Text(
                        text = "${node.activeTrafficRate.toInt()} ops/s",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily.Monospace,
                        color = TextDimmed,
                        fontSize = 9.sp
                    )
                }
            }
        }
    }
}

/**
 * Individual Card displaying a live detected Neural Intent Pattern
 */
@Composable
fun IntentPatternCard(
    pattern: NeuralIntentPattern,
    modifier: Modifier = Modifier
) {
    val riskColor = when (pattern.riskLevel) {
        IntentRiskLevel.SAFE -> OperationalEmerald
        IntentRiskLevel.ELEVATED -> QuantumVioletLight
        IntentRiskLevel.RESTRICTED -> SolarAmber
        IntentRiskLevel.ISOLATED -> ContainmentCrimson
    }

    val timeStr = remember(pattern.timestamp) {
        SimpleDateFormat("HH:mm:ss.SSS", Locale.US).format(Date(pattern.timestamp))
    }

    QuantumGlassCard(
        borderColor = riskColor.copy(alpha = 0.4f),
        backgroundColor = SpaceCobaltGlassElevated,
        modifier = modifier.fillMaxWidth()
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(riskColor)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = pattern.id,
                    style = MaterialTheme.typography.labelMedium,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = AmbientWhite
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "• $timeStr",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = TextDimmed
                )
            }

            PhotonicBadge(
                text = pattern.riskLevel.name,
                signalColor = riskColor
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Intent Description & Classification
        Text(
            text = pattern.classification,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = AmbientWhite
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Node Routing Pathway
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(SpaceCobaltSurface)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = pattern.sourceNode,
                style = MaterialTheme.typography.labelSmall,
                fontFamily = FontFamily.Monospace,
                color = PhotonicCyan,
                fontSize = 10.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = OperationalEmerald,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .size(12.dp)
            )
            Text(
                text = pattern.targetNode,
                style = MaterialTheme.typography.labelSmall,
                fontFamily = FontFamily.Monospace,
                color = OperationalEmeraldLight,
                fontSize = 10.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Telemetry Metrics Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "CONFIDENCE", style = MaterialTheme.typography.bodySmall, fontSize = 10.sp, color = TextDimmed)
                Text(
                    text = "${(pattern.confidenceScore * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = OperationalEmeraldLight
                )
            }

            Column {
                Text(text = "LATENCY", style = MaterialTheme.typography.bodySmall, fontSize = 10.sp, color = TextDimmed)
                Text(
                    text = "${pattern.latencyMs} ms",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = PhotonicCyan
                )
            }

            Column {
                Text(text = "ENTROPY Δ", style = MaterialTheme.typography.bodySmall, fontSize = 10.sp, color = TextDimmed)
                Text(
                    text = "+${pattern.entropyDelta}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = AmbientWhite
                )
            }

            Column {
                Text(text = "SYNCHRONIC HASH", style = MaterialTheme.typography.bodySmall, fontSize = 10.sp, color = TextDimmed)
                Text(
                    text = pattern.synchronicHash,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = QuantumVioletLight
                )
            }
        }
    }
}
