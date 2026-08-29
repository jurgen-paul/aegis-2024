package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AgisArchitectureConstants
import com.example.model.ArchitectureComparison
import com.example.ui.components.CyberNodeArchitectureVisualizer
import com.example.ui.components.PhotonicBadge
import com.example.ui.components.QuantumGlassCard
import com.example.ui.theme.*
import com.example.viewmodel.AgisViewModel

@Composable
fun ArchitectureMatrixScreen(
    viewModel: AgisViewModel
) {
    val context = LocalContext.current
    val inspectedLayerId by viewModel.inspectedLayerId.collectAsState()
    var viewMode by remember { mutableStateOf("CYBER_NODES") } // "CYBER_NODES" or "COMPARISON" or "LAYERS" or "SCHEMA_JSON"

    val cyberNodes by viewModel.cyberNodes.collectAsState()
    val activeNeuralRoutes by viewModel.activeNeuralRoutes.collectAsState()
    val selectedCyberRouteId by viewModel.selectedCyberRouteId.collectAsState()
    val selectedCyberNodeId by viewModel.selectedCyberNodeId.collectAsState()
    val activeHopIndex by viewModel.activeHopIndex.collectAsState()
    val isRouteSimulationRunning by viewModel.isRouteSimulationRunning.collectAsState()

    val currentLayer = AgisArchitectureConstants.ARCHITECTURE_LAYERS.firstOrNull { it.layerId == inspectedLayerId }
        ?: AgisArchitectureConstants.ARCHITECTURE_LAYERS.first()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
    ) {
        // Section Header
        item {
            QuantumGlassCard(
                borderColor = PhotonicCyan.copy(alpha = 0.3f),
                backgroundColor = SpaceCobaltCard
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "AGIS ARCHITECTURE EVOLUTION",
                            style = MaterialTheme.typography.labelMedium,
                            color = PhotonicCyanLight
                        )
                        Text(
                            text = "Cyber-Node & Quantum Core Mesh",
                            style = MaterialTheme.typography.titleMedium,
                            color = AmbientWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    PhotonicBadge(
                        text = "ZERO-TRUST",
                        signalColor = OperationalEmerald,
                        icon = Icons.Default.VerifiedUser
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Mode Selector Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "CYBER_NODES" to "Cyber-Node Canvas",
                        "COMPARISON" to "Matrix (2024/2045)",
                        "LAYERS" to "6-Layer Deep Specs",
                        "SCHEMA_JSON" to "JSON Schema"
                    ).forEach { (mode, label) ->
                        val isSelected = viewMode == mode
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) PhotonicCyan.copy(alpha = 0.25f) else SpaceCobaltGlassElevated)
                                .border(
                                    1.dp,
                                    if (isSelected) PhotonicCyan else SpaceCobaltGlassBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { viewMode = mode }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) PhotonicCyan else AmbientWhiteMuted,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // Mode 0: Interactive Canvas Cyber-Node Architecture Visualizer
        if (viewMode == "CYBER_NODES") {
            item {
                CyberNodeArchitectureVisualizer(
                    cyberNodes = cyberNodes,
                    routes = activeNeuralRoutes,
                    selectedRouteId = selectedCyberRouteId,
                    selectedNodeId = selectedCyberNodeId,
                    activeHopIndex = activeHopIndex,
                    isSimulationRunning = isRouteSimulationRunning,
                    onSelectRoute = { routeId -> viewModel.selectCyberRoute(routeId) },
                    onSelectNode = { nodeId -> viewModel.selectCyberNode(nodeId) },
                    onDispatchPacket = { routeId -> viewModel.dispatchNeuralRoutePacket(routeId) }
                )
            }
        }

        // Mode 1: 2024 vs 2045 Comparison Matrix
        if (viewMode == "COMPARISON") {
            item {
                Text(
                    text = "SEVEN ARCHITECTURAL EVOLUTION LAYERS",
                    style = MaterialTheme.typography.labelLarge,
                    color = PhotonicCyan,
                    fontWeight = FontWeight.Bold
                )
            }

            items(AgisArchitectureConstants.COMPARISON_MATRIX) { item ->
                ComparisonLayerCard(item)
            }
        }

        // Mode 2: 6 Architectural Layers Deep Inspection
        if (viewMode == "LAYERS") {
            item {
                // Layer horizontal selector tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AgisArchitectureConstants.ARCHITECTURE_LAYERS.forEach { layer ->
                        val isSelected = layer.layerId == inspectedLayerId
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) PhotonicCyan.copy(alpha = 0.25f) else SpaceCobaltGlass)
                                .border(
                                    1.dp,
                                    if (isSelected) PhotonicCyan else SpaceCobaltGlassBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { viewModel.setInspectedLayerId(layer.layerId) }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Column {
                                Text(
                                    text = "LAYER ${layer.layerId}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) PhotonicCyan else AmbientWhiteSubtle,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = layer.name.split(" ").take(2).joinToString(" "),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isSelected) AmbientWhite else AmbientWhiteMuted
                                )
                            }
                        }
                    }
                }
            }

            item {
                // Detailed Layer Inspector Card
                QuantumGlassCard(
                    borderColor = PhotonicCyan.copy(alpha = 0.4f),
                    backgroundColor = SpaceCobaltGlassElevated
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "LAYER ${currentLayer.layerId} • ${currentLayer.timestamp}",
                                style = MaterialTheme.typography.labelMedium,
                                color = PhotonicCyanLight,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = currentLayer.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = AmbientWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        PhotonicBadge(
                            text = "ACTIVE TIER",
                            signalColor = OperationalEmerald
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentLayer.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AmbientWhiteMuted
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Core Rule Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SpaceCobaltSurface)
                            .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Column {
                            Text(
                                text = "CORE ARCHITECTURAL MANDATE",
                                style = MaterialTheme.typography.labelSmall,
                                color = SolarAmber,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "\"${currentLayer.coreRule}\"",
                                style = MaterialTheme.typography.bodyMedium,
                                color = AmbientWhite,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Signals & Color Codes
                    Text(
                        text = "PHOTONIC SIGNALS & PALETTE",
                        style = MaterialTheme.typography.labelSmall,
                        color = PhotonicCyan,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    currentLayer.signals.forEach { (signalName, signalValue) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = signalName,
                                style = MaterialTheme.typography.labelSmall,
                                color = AmbientWhiteMuted
                            )
                            Text(
                                text = signalValue,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (signalName.contains("Threat")) ContainmentCrimson
                                else if (signalName.contains("Healthy") || signalName.contains("Enclave")) OperationalEmerald
                                else PhotonicCyanLight,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Architectural Components
                    Text(
                        text = "KEY COMPONENTS & ARCHITECTURAL DATA FLOW",
                        style = MaterialTheme.typography.labelSmall,
                        color = PhotonicCyan,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    currentLayer.components.forEach { comp ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "▸ ",
                                style = MaterialTheme.typography.bodySmall,
                                color = PhotonicCyan
                            )
                            Text(
                                text = comp,
                                style = MaterialTheme.typography.bodySmall,
                                color = AmbientWhite
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Specifications
                    Text(
                        text = "TECHNICAL SPECIFICATIONS",
                        style = MaterialTheme.typography.labelSmall,
                        color = QuantumVioletLight,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    currentLayer.specs.forEach { (specKey, specVal) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = specKey,
                                style = MaterialTheme.typography.labelSmall,
                                color = AmbientWhiteMuted
                            )
                            Text(
                                text = specVal,
                                style = MaterialTheme.typography.labelSmall,
                                color = AmbientWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Mode 3: JSON Schema Exporter
        if (viewMode == "SCHEMA_JSON") {
            item {
                val jsonText = remember { AgisArchitectureConstants.generateAgis2045Json() }
                QuantumGlassCard(
                    borderColor = PhotonicCyan.copy(alpha = 0.4f),
                    backgroundColor = SpaceCobaltGlassElevated
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "AGIS-2045 JSON SCHEMA DEFINITION",
                            style = MaterialTheme.typography.labelMedium,
                            color = PhotonicCyanLight,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("AGIS-2045 Architecture JSON", jsonText)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "AGIS-2045 JSON copied to clipboard!", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy JSON",
                                tint = PhotonicCyan
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SpaceCobaltDark)
                            .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = jsonText,
                            style = MaterialTheme.typography.labelSmall,
                            color = AmbientWhite,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ComparisonLayerCard(item: ArchitectureComparison) {
    QuantumGlassCard(
        borderColor = PhotonicCyan.copy(alpha = 0.25f),
        backgroundColor = SpaceCobaltGlassElevated
    ) {
        Text(
            text = item.layerName.uppercase(),
            style = MaterialTheme.typography.titleSmall,
            color = PhotonicCyanLight,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 2024 Base Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(SpaceCobaltSurface)
                .border(1.dp, LegacyMarineBlue.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .padding(10.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(LegacySteel)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "AGIS-2024 BASE MODEL",
                        style = MaterialTheme.typography.labelSmall,
                        color = AmbientWhiteSubtle,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.year2024Base,
                    style = MaterialTheme.typography.bodySmall,
                    color = AmbientWhiteMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 2045 Quantum Core Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(SpaceCobaltDark)
                .border(1.dp, PhotonicCyan.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .padding(10.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(PhotonicCyan)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "AGIS-2045 QUANTUM CORE",
                        style = MaterialTheme.typography.labelSmall,
                        color = PhotonicCyan,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.year2045Quantum,
                    style = MaterialTheme.typography.bodySmall,
                    color = AmbientWhite,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = OperationalEmerald,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = item.securityBenefit,
                style = MaterialTheme.typography.labelSmall,
                color = OperationalEmerald
            )
        }
    }
}
