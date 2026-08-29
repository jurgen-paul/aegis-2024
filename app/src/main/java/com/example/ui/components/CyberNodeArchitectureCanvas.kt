package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CyberNode
import com.example.model.CyberNodeRoute
import com.example.model.IntentRiskLevel
import com.example.ui.animation.PhotonicSignalPulseIndicator
import com.example.ui.animation.QuantumVolumetricButton
import com.example.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Canvas-based Dynamic Cyber-Node Architecture & Neural Intent Route Visualizer.
 * Dynamically renders cubic bezier paths between Cyber-Nodes based on active neural intent routes,
 * displaying traveling photon packets, real-time hop execution, and multi-tier security bounds.
 */
@Composable
fun CyberNodeArchitectureVisualizer(
    cyberNodes: List<CyberNode>,
    routes: List<CyberNodeRoute>,
    selectedRouteId: String,
    selectedNodeId: String?,
    activeHopIndex: Int,
    isSimulationRunning: Boolean,
    onSelectRoute: (String) -> Unit,
    onSelectNode: (String?) -> Unit,
    onDispatchPacket: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeRoute = remember(routes, selectedRouteId) {
        routes.firstOrNull { it.id == selectedRouteId } ?: routes.firstOrNull()
    }

    val selectedNode = remember(cyberNodes, selectedNodeId) {
        cyberNodes.firstOrNull { it.id == selectedNodeId }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Section Header Card
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
                        pulseSpeedMs = 1200
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "CYBER-NODE ARCHITECTURE MESH",
                            style = MaterialTheme.typography.labelSmall,
                            color = PhotonicCyan,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Dynamic Neural Intent Route Canvas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AmbientWhite
                        )
                    }
                }

                PhotonicBadge(
                    text = "${cyberNodes.size} NODES • ${routes.size} ROUTES",
                    signalColor = PhotonicCyan,
                    icon = Icons.Default.Hub
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Route Selector Horizontal Chips
            Text(
                text = "ACTIVE NEURAL INTENT ROUTE:",
                style = MaterialTheme.typography.labelSmall,
                color = AmbientWhiteMuted,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                routes.forEach { route ->
                    val isSelected = route.id == selectedRouteId
                    val riskColor = when (route.riskLevel) {
                        IntentRiskLevel.SAFE -> OperationalEmerald
                        IntentRiskLevel.ELEVATED -> QuantumVioletLight
                        IntentRiskLevel.RESTRICTED -> SolarAmber
                        IntentRiskLevel.ISOLATED -> ContainmentCrimson
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (isSelected) PhotonicCyan.copy(alpha = 0.25f)
                                else SpaceCobaltSurface
                            )
                            .border(
                                1.dp,
                                if (isSelected) PhotonicCyan else SpaceCobaltGlassBorder,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { onSelectRoute(route.id) }
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(riskColor)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = route.name,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) PhotonicCyan else AmbientWhiteMuted
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "[${route.nodeHops.size} Hops]",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 9.sp,
                                color = if (isSelected) OperationalEmeraldLight else TextDimmed
                            )
                        }
                    }
                }
            }
        }

        // Dedicated Canvas Card for Dynamic Cyber-Node Topology
        QuantumGlassCard(
            borderColor = OperationalEmerald.copy(alpha = 0.45f),
            backgroundColor = SpaceCobaltDark
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (activeRoute != null) activeRoute.name.uppercase() else "DYNAMIC INTENT TOPOLOGY",
                        style = MaterialTheme.typography.labelSmall,
                        color = OperationalEmeraldLight,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (activeRoute != null) activeRoute.description else "Interactive node paths dynamically trace neural intent flow",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 11.sp,
                        color = AmbientWhiteMuted
                    )
                }

                if (isSimulationRunning) {
                    PhotonicBadge(
                        text = "PACKET IN FLIGHT",
                        signalColor = SolarAmber,
                        icon = Icons.Default.DirectionsRun,
                        enablePulse = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // The Cyber-Node Canvas
            CyberNodeMeshCanvas(
                nodes = cyberNodes,
                activeRoute = activeRoute,
                selectedNodeId = selectedNodeId,
                activeHopIndex = activeHopIndex,
                isSimulationRunning = isSimulationRunning,
                onNodeClicked = { nodeId -> onSelectNode(nodeId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Active Route Hop Sequence Strip
            if (activeRoute != null) {
                Text(
                    text = "NEURAL ROUTE HOP SEQUENCE:",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextDimmed,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    activeRoute.nodeHops.forEachIndexed { index, nodeId ->
                        val nodeObj = cyberNodes.firstOrNull { it.id == nodeId }
                        val isCurrentHop = isSimulationRunning && activeHopIndex == index
                        val isPassedHop = isSimulationRunning && activeHopIndex > index

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(
                                    when {
                                        isCurrentHop -> SolarAmber.copy(alpha = 0.35f)
                                        isPassedHop -> OperationalEmerald.copy(alpha = 0.25f)
                                        else -> SpaceCobaltGlassElevated
                                    }
                                )
                                .border(
                                    1.dp,
                                    when {
                                        isCurrentHop -> SolarAmber
                                        isPassedHop -> OperationalEmerald
                                        else -> SpaceCobaltGlassBorder
                                    },
                                    RoundedCornerShape(6.dp)
                                )
                                .clickable { onSelectNode(nodeId) }
                                .padding(horizontal = 8.dp, vertical = 5.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "T${nodeObj?.tierNumber ?: (index + 1)}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = if (isCurrentHop) SolarAmber else PhotonicCyanLight
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = nodeObj?.shortLabel ?: nodeId.replace("NODE_", ""),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 11.sp,
                                    color = if (isCurrentHop) AmbientWhite else if (isPassedHop) OperationalEmeraldLight else AmbientWhiteMuted
                                )
                            }
                        }

                        if (index < activeRoute.nodeHops.size - 1) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Hop link",
                                tint = if (isPassedHop) OperationalEmerald else SpaceCobaltGlassBorder,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Action Dispatch Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuantumVolumetricButton(
                        text = if (isSimulationRunning) "PACKET PROPAGATING..." else "DISPATCH INTENT PACKET",
                        icon = if (isSimulationRunning) Icons.Default.Sensors else Icons.Default.Send,
                        primaryColor = if (activeRoute.riskLevel == IntentRiskLevel.RESTRICTED) SolarAmber else OperationalEmerald,
                        secondaryColor = PhotonicCyan,
                        containerColor = SpaceCobaltDark,
                        modifier = Modifier.weight(1f),
                        shapeRadius = 10.dp,
                        onClick = {
                            if (!isSimulationRunning) {
                                onDispatchPacket(activeRoute.id)
                            }
                        }
                    )
                }
            }
        }

        // Selected Node Detailed Inspector Card
        AnimatedVisibility(
            visible = selectedNode != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            if (selectedNode != null) {
                QuantumGlassCard(
                    borderColor = if (selectedNode.isHardwareEnclave) QuantumVioletLight.copy(alpha = 0.6f) else PhotonicCyan.copy(alpha = 0.5f),
                    backgroundColor = SpaceCobaltGlassElevated
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (selectedNode.isHardwareEnclave) QuantumVioletDark else SpaceCobaltSurface)
                                    .border(1.dp, if (selectedNode.isHardwareEnclave) QuantumVioletLight else PhotonicCyan, RoundedCornerShape(6.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "T${selectedNode.tierNumber}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedNode.isHardwareEnclave) QuantumVioletLight else PhotonicCyan
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = selectedNode.name,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = AmbientWhite
                                )
                                Text(
                                    text = selectedNode.tierLabel,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PhotonicCyanLight
                                )
                            }
                        }

                        IconButton(onClick = { onSelectNode(null) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Inspector",
                                tint = AmbientWhiteMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = selectedNode.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = AmbientWhiteMuted
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Node Specs Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(SpaceCobaltSurface)
                                .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(6.dp))
                                .padding(8.dp)
                        ) {
                            Column {
                                Text(
                                    text = "SECURITY PROTOCOL",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextDimmed,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = selectedNode.securityProtocol,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OperationalEmeraldLight,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 1
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(SpaceCobaltSurface)
                                .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(6.dp))
                                .padding(8.dp)
                        ) {
                            Column {
                                Text(
                                    text = "LATENCY / PACKETS",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextDimmed,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${selectedNode.latencyNs} ns • ${selectedNode.activePackets} ops",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PhotonicCyanLight,
                                    fontWeight = FontWeight.SemiBold
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
 * High-Performance Jetpack Compose Canvas rendering the Cyber-Node Architecture Mesh.
 * Draws dynamic multi-hop curved paths, traveling photon packets, and glowing cyber-nodes.
 */
@Composable
fun CyberNodeMeshCanvas(
    nodes: List<CyberNode>,
    activeRoute: CyberNodeRoute?,
    selectedNodeId: String?,
    activeHopIndex: Int,
    isSimulationRunning: Boolean,
    onNodeClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "CyberNodeAnimation")

    // Photon travel phase (0.0 to 1.0)
    val photonPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "PhotonPhase"
    )

    // Pulse halo phase
    val pulseHalo by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "PulseHalo"
    )

    // Ambient lattice shimmer
    val latticeShimmer by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "LatticeShimmer"
    )

    // Cache of computed node screen coordinates
    var canvasWidth by remember { mutableFloatStateOf(0f) }
    var canvasHeight by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SpaceCobaltDark.copy(alpha = 0.98f))
            .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(12.dp))
            .pointerInput(nodes) {
                detectTapGestures { tapOffset ->
                    if (canvasWidth > 0 && canvasHeight > 0) {
                        // Find closest node to tap point within 36dp threshold
                        val hitRadius = 36.dp.toPx()
                        val clickedNode = nodes.minByOrNull { node ->
                            val nx = node.normalizedX * canvasWidth
                            val ny = node.normalizedY * canvasHeight
                            val dx = tapOffset.x - nx
                            val dy = tapOffset.y - ny
                            sqrt(dx * dx + dy * dy)
                        }

                        if (clickedNode != null) {
                            val nx = clickedNode.normalizedX * canvasWidth
                            val ny = clickedNode.normalizedY * canvasHeight
                            val dist = sqrt((tapOffset.x - nx) * (tapOffset.x - nx) + (tapOffset.y - ny) * (tapOffset.y - ny))
                            if (dist <= hitRadius) {
                                onNodeClicked(clickedNode.id)
                            }
                        }
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            canvasWidth = size.width
            canvasHeight = size.height
            val w = size.width
            val h = size.height

            // 1. Draw subtle background coordinate lattice
            drawBackgroundLattice(w, h, latticeShimmer)

            // Map each node to its exact canvas pixel position
            val nodeCoords = nodes.associate { node ->
                node.id to Offset(node.normalizedX * w, node.normalizedY * h)
            }

            // 2. Draw dormant structural interconnect mesh with ambient glowing emerald telemetry pulses
            drawStructuralMesh(nodes, nodeCoords, photonPhase)

            // 3. Draw active neural intent route dynamically!
            if (activeRoute != null && activeRoute.nodeHops.size >= 2) {
                drawActiveNeuralRoute(
                    route = activeRoute,
                    nodeCoords = nodeCoords,
                    photonPhase = photonPhase,
                    activeHopIndex = activeHopIndex,
                    isSimulationRunning = isSimulationRunning,
                    pulseHalo = pulseHalo
                )
            }

            // 4. Draw each Cyber-Node glyph
            nodes.forEach { node ->
                val coord = nodeCoords[node.id] ?: return@forEach
                val isSelected = node.id == selectedNodeId
                val isInActiveRoute = activeRoute?.nodeHops?.contains(node.id) == true
                val isCurrentActiveHop = isSimulationRunning && activeRoute != null &&
                        activeHopIndex < activeRoute.nodeHops.size &&
                        activeRoute.nodeHops[activeHopIndex] == node.id

                drawCyberNodeGlyph(
                    node = node,
                    center = coord,
                    isSelected = isSelected,
                    isInActiveRoute = isInActiveRoute,
                    isCurrentActiveHop = isCurrentActiveHop,
                    pulseHalo = pulseHalo
                )
            }
        }

        // HTML/Compose Text Overlay for Node Labels (guaranteeing crystal-clear high-DPI rendering)
        nodes.forEach { node ->
            val isSelected = node.id == selectedNodeId
            val isEnclave = node.isHardwareEnclave
            val isInActiveRoute = activeRoute?.nodeHops?.contains(node.id) == true

            // Positioning offset for node label
            val isTopNode = node.normalizedY < 0.25f
            val isLeftNode = node.normalizedX < 0.35f
            val isRightNode = node.normalizedX > 0.65f

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = (node.normalizedX * 300).dp.coerceIn(8.dp, 230.dp),
                        top = (node.normalizedY * 260 + (if (isTopNode) 22f else -26f)).dp.coerceIn(4.dp, 270.dp)
                    )
            ) {
                Column(
                    horizontalAlignment = if (isLeftNode) Alignment.Start else if (isRightNode) Alignment.End else Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = node.shortLabel,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = when {
                            isSelected -> OperationalEmeraldLight
                            isEnclave -> QuantumVioletLight
                            isInActiveRoute -> PhotonicCyanLight
                            else -> AmbientWhiteMuted
                        }
                    )
                    Text(
                        text = "T${node.tierNumber} • ${(node.activeLoad * 100).toInt()}% load",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 9.sp,
                        color = if (isSelected) OperationalEmerald else TextDimmed
                    )
                }
            }
        }
    }
}

/**
 * Draws background quantum coordinate grid lines.
 */
private fun DrawScope.drawBackgroundLattice(w: Float, h: Float, shimmer: Float) {
    val stepX = w / 6f
    val stepY = h / 6f

    val gridColor = SpaceCobaltGlassBorder.copy(alpha = 0.25f)
    for (i in 1..5) {
        drawLine(
            color = gridColor,
            start = Offset(i * stepX, 0f),
            end = Offset(i * stepX, h),
            strokeWidth = 0.8.dp.toPx()
        )
        drawLine(
            color = gridColor,
            start = Offset(0f, i * stepY),
            end = Offset(w, i * stepY),
            strokeWidth = 0.8.dp.toPx()
        )
    }
}

/**
 * Draws structural architectural connections between Cyber-Nodes with ambient glowing emerald pulses.
 */
private fun DrawScope.drawStructuralMesh(
    nodes: List<CyberNode>,
    nodeCoords: Map<String, Offset>,
    photonPhase: Float
) {
    val structuralPairs = listOf(
        "NODE_COMPOSERY" to "NODE_VIEWMODEL",
        "NODE_COMPOSERY" to "NODE_POLICY_GATE",
        "NODE_COMPOSERY" to "NODE_ORACLE_SWARM",
        "NODE_VIEWMODEL" to "NODE_ORACLE_SWARM",
        "NODE_POLICY_GATE" to "NODE_ORACLE_SWARM",
        "NODE_VIEWMODEL" to "NODE_ENCLAVE_VAULT",
        "NODE_POLICY_GATE" to "NODE_BOUNDARY_GATEWAY",
        "NODE_ORACLE_SWARM" to "NODE_TELEMETRY_SANITIZER",
        "NODE_ENCLAVE_VAULT" to "NODE_TELEMETRY_SANITIZER",
        "NODE_TELEMETRY_SANITIZER" to "NODE_BOUNDARY_GATEWAY",
        "NODE_ENCLAVE_VAULT" to "NODE_BOUNDARY_GATEWAY"
    )

    structuralPairs.forEachIndexed { pairIndex, (srcId, dstId) ->
        val src = nodeCoords[srcId]
        val dst = nodeCoords[dstId]
        if (src != null && dst != null) {
            // Background subtle dashed interconnect
            drawLine(
                color = SpaceCobaltGlassBorder.copy(alpha = 0.35f),
                start = src,
                end = dst,
                strokeWidth = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
            )

            // Ambient glowing emerald telemetry pulse traveling across structural link
            val ambientT = (photonPhase * 0.7f + (pairIndex * 0.13f)) % 1f
            val packetX = src.x + (dst.x - src.x) * ambientT
            val packetY = src.y + (dst.y - src.y) * ambientT
            val packetPos = Offset(packetX, packetY)

            // Subtle emerald glow halo
            drawCircle(
                color = OperationalEmerald.copy(alpha = 0.25f),
                radius = 3.5.dp.toPx(),
                center = packetPos
            )
            // Core emerald telemetry dot
            drawCircle(
                color = OperationalEmeraldLight.copy(alpha = 0.6f),
                radius = 1.5.dp.toPx(),
                center = packetPos
            )
        }
    }
}

/**
 * Dynamically renders the active neural intent route as a continuous glowing photonic bezier spline
 * with traveling data packet particles flowing as glowing emerald pulses with comet tails,
 * orbital sparks, and node arrival shockwaves.
 */
private fun DrawScope.drawActiveNeuralRoute(
    route: CyberNodeRoute,
    nodeCoords: Map<String, Offset>,
    photonPhase: Float,
    activeHopIndex: Int,
    isSimulationRunning: Boolean,
    pulseHalo: Float
) {
    val hops = route.nodeHops
    if (hops.size < 2) return

    val isSafeOrEmerald = route.riskLevel == IntentRiskLevel.SAFE
    val emeraldPulseColor = OperationalEmerald
    val emeraldPulseLight = OperationalEmeraldLight
    val emeraldGlowColor = PhotonicCyan

    // Primary route beam colors
    val primaryBeamColor = when (route.riskLevel) {
        IntentRiskLevel.SAFE -> OperationalEmerald
        IntentRiskLevel.ELEVATED -> QuantumVioletLight
        IntentRiskLevel.RESTRICTED -> SolarAmber
        IntentRiskLevel.ISOLATED -> ContainmentCrimson
    }

    val glowBeamColor = when (route.riskLevel) {
        IntentRiskLevel.SAFE -> PhotonicCyan
        IntentRiskLevel.ELEVATED -> QuantumVioletDark
        IntentRiskLevel.RESTRICTED -> SolarAmberDark
        IntentRiskLevel.ISOLATED -> ContainmentCrimsonDark
    }

    // Step through each sequential hop segment in the active route
    for (i in 0 until hops.size - 1) {
        val srcCoord = nodeCoords[hops[i]] ?: continue
        val dstCoord = nodeCoords[hops[i + 1]] ?: continue

        val isCurrentSimSegment = isSimulationRunning && activeHopIndex == i
        val isCompletedSimSegment = isSimulationRunning && activeHopIndex > i

        // Calculate dynamic curvature control point
        val midX = (srcCoord.x + dstCoord.x) / 2f
        val midY = (srcCoord.y + dstCoord.y) / 2f
        val dx = dstCoord.x - srcCoord.x
        val dy = dstCoord.y - srcCoord.y
        val dist = sqrt(dx * dx + dy * dy).coerceAtLeast(1f)

        // Perpendicular offset for curved visual arc
        val perpX = -dy / dist * 18f
        val perpY = dx / dist * 18f
        val controlPoint = Offset(midX + perpX, midY + perpY)

        val path = Path().apply {
            moveTo(srcCoord.x, srcCoord.y)
            quadraticTo(controlPoint.x, controlPoint.y, dstCoord.x, dstCoord.y)
        }

        // Layer 1: Broad soft photonic glow along curve
        drawPath(
            path = path,
            brush = Brush.linearGradient(
                colors = listOf(
                    glowBeamColor.copy(alpha = if (isCurrentSimSegment) 0.85f else 0.4f),
                    primaryBeamColor.copy(alpha = if (isCurrentSimSegment) 0.9f else 0.45f)
                ),
                start = srcCoord,
                end = dstCoord
            ),
            style = Stroke(
                width = (if (isCurrentSimSegment) 6.dp else 4.dp).toPx(),
                cap = StrokeCap.Round
            )
        )

        // Layer 2: Core sharp synchronic beam
        drawPath(
            path = path,
            brush = Brush.linearGradient(
                colors = listOf(
                    if (isCurrentSimSegment) AmbientWhite else emeraldPulseLight,
                    primaryBeamColor
                ),
                start = srcCoord,
                end = dstCoord
            ),
            style = Stroke(
                width = (if (isCurrentSimSegment) 2.5.dp else 1.8.dp).toPx(),
                cap = StrokeCap.Round
            )
        )

        // Helper to evaluate quadratic Bezier coordinate at parameter t [0..1]
        fun bezierPoint(t: Float): Offset {
            val clampedT = t.coerceIn(0f, 1f)
            val oneMinusT = 1f - clampedT
            val px = oneMinusT * oneMinusT * srcCoord.x + 2f * oneMinusT * clampedT * controlPoint.x + clampedT * clampedT * dstCoord.x
            val py = oneMinusT * oneMinusT * srcCoord.y + 2f * oneMinusT * clampedT * controlPoint.y + clampedT * clampedT * dstCoord.y
            return Offset(px, py)
        }

        // Multiple packet pulses per segment (Lead packet + Staggered secondary packet)
        val packetOffsets = listOf(0.0f, 0.5f)

        packetOffsets.forEachIndexed { pulseIndex, phaseOffset ->
            val segmentT = ((photonPhase + (i.toFloat() / hops.size) + phaseOffset) % 1f)
            val packetCoord = bezierPoint(segmentT)

            // 1. Particle Comet Tail (Trailing glowing emerald sparks)
            val tailParticlesCount = if (isCurrentSimSegment) 8 else 5
            for (trailIdx in tailParticlesCount downTo 1) {
                val lagAmount = trailIdx * 0.025f
                val tailT = (segmentT - lagAmount).coerceAtLeast(0f)
                val tailCoord = bezierPoint(tailT)
                val decayFraction = 1f - (trailIdx.toFloat() / tailParticlesCount)
                val tailAlpha = (decayFraction * (if (isCurrentSimSegment) 0.75f else 0.5f)).coerceIn(0f, 1f)
                val tailRadius = (1.5.dp + 2.5.dp * decayFraction).toPx()

                // Trail glow
                drawCircle(
                    color = emeraldPulseColor.copy(alpha = tailAlpha * 0.6f),
                    radius = tailRadius * 1.6f,
                    center = tailCoord
                )
                // Trail core particle
                drawCircle(
                    color = emeraldPulseLight.copy(alpha = tailAlpha),
                    radius = tailRadius,
                    center = tailCoord
                )
            }

            // 2. Primary Glowing Emerald Data Packet Pulse
            val pulseBloomRadius = (if (isCurrentSimSegment) 14.dp else 9.dp).toPx()
            val pulseCoreRadius = (if (isCurrentSimSegment) 7.dp else 4.5.dp).toPx()
            val whiteSparkRadius = (if (isCurrentSimSegment) 3.5.dp else 2.2.dp).toPx()

            // Outer soft radiant emerald bloom
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        emeraldPulseLight.copy(alpha = if (isCurrentSimSegment) 0.85f else 0.55f),
                        emeraldPulseColor.copy(alpha = if (isCurrentSimSegment) 0.45f else 0.25f),
                        Color.Transparent
                    ),
                    center = packetCoord,
                    radius = pulseBloomRadius
                ),
                radius = pulseBloomRadius,
                center = packetCoord
            )

            // Middle intense emerald energy packet
            drawCircle(
                color = if (isCurrentSimSegment && pulseIndex == 0) SolarAmber else emeraldPulseColor,
                radius = pulseCoreRadius,
                center = packetCoord
            )

            // Hot-white center photon spark
            drawCircle(
                color = AmbientWhite,
                radius = whiteSparkRadius,
                center = packetCoord
            )

            // 3. Orbiting Micro-Sparks around lead packet
            if (pulseIndex == 0) {
                val orbitAngle = (photonPhase * 2f * PI + i * 1.5f).toFloat()
                val orbitOffsetFloat = 8f + 3f * sin(photonPhase * 4f * PI).toFloat()
                val orbitRadius = orbitOffsetFloat.dp.toPx()
                for (sparkIdx in 0..2) {
                    val sparkAngle = orbitAngle + (sparkIdx * (2f * PI / 3f)).toFloat()
                    val sparkX = packetCoord.x + orbitRadius * cos(sparkAngle)
                    val sparkY = packetCoord.y + orbitRadius * sin(sparkAngle) * 0.6f // slightly elliptical orbit
                    drawCircle(
                        color = emeraldPulseLight.copy(alpha = 0.8f),
                        radius = 1.4.dp.toPx(),
                        center = Offset(sparkX, sparkY)
                    )
                }
            }

            // 4. Node Arrival Burst Shockwave when packet approaches destination
            if (segmentT > 0.88f) {
                val arrivalFactor = ((segmentT - 0.88f) / 0.12f).coerceIn(0f, 1f)
                val shockwaveRadius = 6.dp.toPx() + (18.dp.toPx() * arrivalFactor)
                val shockwaveAlpha = (1f - arrivalFactor) * 0.7f

                // Expanding arrival ring at destination node
                drawCircle(
                    color = emeraldPulseLight.copy(alpha = shockwaveAlpha),
                    radius = shockwaveRadius,
                    center = dstCoord,
                    style = Stroke(width = 1.5.dp.toPx())
                )

                // Radial sparkle burst
                val sparkCount = 6
                for (s in 0 until sparkCount) {
                    val sAngle = (s * (2f * PI / sparkCount) + arrivalFactor).toFloat()
                    val sDist = 8.dp.toPx() + (14.dp.toPx() * arrivalFactor)
                    val sx = dstCoord.x + sDist * cos(sAngle)
                    val sy = dstCoord.y + sDist * sin(sAngle)
                    drawCircle(
                        color = OperationalEmeraldLight.copy(alpha = shockwaveAlpha),
                        radius = (1.8f * (1f - arrivalFactor)).dp.toPx(),
                        center = Offset(sx, sy)
                    )
                }
            }
        }

        // Active simulation hop highlight
        if (isCurrentSimSegment) {
            val shockRadius = 16.dp.toPx() * (1f + pulseHalo * 0.9f)
            drawCircle(
                color = SolarAmber.copy(alpha = (1f - pulseHalo) * 0.85f),
                radius = shockRadius,
                center = dstCoord,
                style = Stroke(width = 2.dp.toPx())
            )
        }
    }
}

/**
 * Draws a single Cyber-Node glyph with multi-ring status halos, load circumference arc, and tier styling.
 */
private fun DrawScope.drawCyberNodeGlyph(
    node: CyberNode,
    center: Offset,
    isSelected: Boolean,
    isInActiveRoute: Boolean,
    isCurrentActiveHop: Boolean,
    pulseHalo: Float
) {
    val isEnclave = node.isHardwareEnclave
    val baseColor = when {
        isCurrentActiveHop -> SolarAmber
        isSelected -> OperationalEmerald
        isEnclave -> QuantumVioletLight
        isInActiveRoute -> PhotonicCyan
        else -> SpaceCobaltGlassBorder
    }

    val coreRadius = (if (isSelected || isCurrentActiveHop) 16.dp else 12.dp).toPx()

    // 1. Concentric pulse ring for active or selected nodes
    if (isInActiveRoute || isSelected || isCurrentActiveHop) {
        val ringRadius = coreRadius * (1.3f + pulseHalo * 0.5f)
        drawCircle(
            color = baseColor.copy(alpha = (1f - pulseHalo) * 0.55f),
            radius = ringRadius,
            center = center,
            style = Stroke(width = 1.5.dp.toPx())
        )
    }

    // 2. Node Background Glass Fill
    drawCircle(
        color = if (isEnclave) SpaceCobaltDark else SpaceCobaltSurface,
        radius = coreRadius,
        center = center
    )

    // 3. Node Outer Rim Stroke
    drawCircle(
        color = baseColor,
        radius = coreRadius,
        center = center,
        style = Stroke(width = (if (isSelected || isCurrentActiveHop) 2.5.dp else 1.8.dp).toPx())
    )

    // 4. Load Indicator Arc (sweeps around circumference based on activeLoad)
    val sweepAngle = node.activeLoad * 360f
    drawArc(
        brush = Brush.sweepGradient(
            colors = listOf(PhotonicCyan, OperationalEmerald, PhotonicCyan),
            center = center
        ),
        startAngle = -90f,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = Offset(center.x - coreRadius - 3.dp.toPx(), center.y - coreRadius - 3.dp.toPx()),
        size = androidx.compose.ui.geometry.Size((coreRadius + 3.dp.toPx()) * 2, (coreRadius + 3.dp.toPx()) * 2),
        style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    )

    // 5. Core Center Glyph Dot
    drawCircle(
        color = if (isCurrentActiveHop) SolarAmber else if (isSelected) OperationalEmeraldLight else AmbientWhite,
        radius = (if (isSelected || isCurrentActiveHop) 4.5.dp else 3.dp).toPx(),
        center = center
    )
}
