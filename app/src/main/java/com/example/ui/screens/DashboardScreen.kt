package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AgisArchitectureConstants
import com.example.model.ThreatSeverity
import com.example.ui.components.BiometricHeader
import com.example.ui.components.PhotonicBadge
import com.example.ui.components.QuantumGlassCard
import com.example.ui.components.SubAgentThreadCard
import com.example.ui.theme.*
import com.example.viewmodel.AgisViewModel

@Composable
fun DashboardScreen(
    viewModel: AgisViewModel,
    onNavigateToTab: (Int) -> Unit,
    onInspectLayer: (Int) -> Unit
) {
    val biometrics by viewModel.biometrics.collectAsState()
    val subAgents by viewModel.subAgents.collectAsState()
    val enclaveKey by viewModel.enclaveKey.collectAsState()
    val glassDepth by viewModel.glassDepth.collectAsState()
    val threatLevel by viewModel.globalThreatLevel.collectAsState()
    val threatIncidents by viewModel.threatIncidents.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp)
    ) {
        // Biometrics Real-Time Header
        item {
            BiometricHeader(
                biometrics = biometrics,
                threatSeverity = threatLevel
            )
        }

        // Volumetric Glass Depth & System Mode Bar
        item {
            QuantumGlassCard(
                borderColor = PhotonicCyan.copy(alpha = 0.3f),
                backgroundColor = SpaceCobaltSurface
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "QUANTUM GLASS DEPTH",
                            style = MaterialTheme.typography.labelSmall,
                            color = PhotonicCyanLight
                        )
                        Text(
                            text = glassDepth,
                            style = MaterialTheme.typography.titleSmall,
                            color = AmbientWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf("2D Clean", "2.5D Volumetric", "3D Quantum").forEach { mode ->
                            val isSelected = glassDepth.startsWith(mode.take(2))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) PhotonicCyan.copy(alpha = 0.25f) else SpaceCobaltGlass)
                                    .border(
                                        1.dp,
                                        if (isSelected) PhotonicCyan else SpaceCobaltGlassBorder,
                                        RoundedCornerShape(6.dp)
                                    )
                                    .clickable { viewModel.setGlassDepth("$mode Volumetric") }
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = mode.take(3),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) PhotonicCyan else AmbientWhiteMuted,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Active Threat Alert Banner (If Any)
        if (threatLevel == ThreatSeverity.CRITICAL) {
            item {
                QuantumGlassCard(
                    borderColor = ContainmentCrimson,
                    backgroundColor = ContainmentCrimsonDark.copy(alpha = 0.3f)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Active Threat",
                                tint = ContainmentCrimson,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "PHOTONIC CRIMSON SHIELD ENGAGED",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = ContainmentCrimson,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Active threat quarantined in non-exportable hardware enclave.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AmbientWhite
                                )
                            }
                        }
                    }
                }
            }
        }

        // Cyber-Node Architecture Canvas Spotlight Card
        item {
            QuantumGlassCard(
                borderColor = PhotonicCyan.copy(alpha = 0.5f),
                backgroundColor = SpaceCobaltGlassElevated,
                onClick = { onNavigateToTab(1) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Hub,
                            contentDescription = "Cyber-Node Architecture",
                            tint = PhotonicCyan,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "CYBER-NODE ARCHITECTURE CANVAS",
                                style = MaterialTheme.typography.labelSmall,
                                color = PhotonicCyan,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Dynamic Neural Intent Route Visualizer",
                                style = MaterialTheme.typography.titleSmall,
                                color = AmbientWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Navigate to Architecture",
                        tint = OperationalEmerald
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Interactive biomorphic mesh dynamically paths multi-hop intent routes across 7 cyber tiers with traveling photon packets.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AmbientWhiteMuted,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    PhotonicBadge(
                        text = "7 CYBER-NODES",
                        signalColor = PhotonicCyan
                    )
                    PhotonicBadge(
                        text = "4 NEURAL INTENT ROUTES",
                        signalColor = OperationalEmerald
                    )
                    PhotonicBadge(
                        text = "512-BIT ENCLAVE",
                        signalColor = QuantumVioletLight
                    )
                }
            }
        }

        // Real-Time Telemetry & Threat Dashboard Spotlight Card
        item {
            QuantumGlassCard(
                borderColor = OperationalEmerald.copy(alpha = 0.5f),
                backgroundColor = SpaceCobaltGlassElevated,
                onClick = { onNavigateToTab(5) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = "Telemetry & Threat Dashboard",
                            tint = OperationalEmerald,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "REAL-TIME TELEMETRY & THREAT MATRIX",
                                style = MaterialTheme.typography.labelSmall,
                                color = OperationalEmeraldLight,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Sanitization Throughput & Anomaly Waves",
                                style = MaterialTheme.typography.titleSmall,
                                color = AmbientWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Navigate to Validation",
                        tint = OperationalEmerald
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Live multi-series area chart streaming ingress vs sanitized egress (KB/s), differential privacy ε=0.50 noise rates, and adversarial anomaly spectrum.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AmbientWhiteMuted,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    PhotonicBadge(
                        text = "LIVE THROUGHPUT WAVES",
                        signalColor = OperationalEmerald
                    )
                    PhotonicBadge(
                        text = "ε = 0.50 DP NOISE",
                        signalColor = PhotonicCyan
                    )
                    PhotonicBadge(
                        text = "THREAT SPECTRUM",
                        signalColor = ContainmentCrimson
                    )
                }
            }
        }

        // 6-Layer Architecture Quick Navigation Grid
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "AGIS-2045 ARCHITECTURAL TIERS",
                        style = MaterialTheme.typography.labelLarge,
                        color = PhotonicCyan,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "EXPLORE MATRIX",
                        style = MaterialTheme.typography.labelSmall,
                        color = OperationalEmerald,
                        modifier = Modifier.clickable { onNavigateToTab(1) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    AgisArchitectureConstants.ARCHITECTURE_LAYERS.chunked(2).forEach { rowLayers ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowLayers.forEach { layer ->
                                QuantumGlassCard(
                                    modifier = Modifier.weight(1f),
                                    borderColor = when (layer.layerId) {
                                        1 -> PhotonicCyan.copy(alpha = 0.4f)
                                        2 -> QuantumViolet.copy(alpha = 0.4f)
                                        3 -> OperationalEmerald.copy(alpha = 0.4f)
                                        4 -> ContainmentCrimson.copy(alpha = 0.4f)
                                        5 -> SolarAmber.copy(alpha = 0.4f)
                                        else -> PhotonicCyanLight.copy(alpha = 0.4f)
                                    },
                                    backgroundColor = SpaceCobaltGlassElevated,
                                    onClick = {
                                        onInspectLayer(layer.layerId)
                                        onNavigateToTab(1)
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "L-${layer.layerId}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = PhotonicCyan,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = layer.timestamp,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AmbientWhiteSubtle
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = layer.name,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = AmbientWhite,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = layer.subtitle,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AmbientWhiteMuted,
                                        maxLines = 2
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Active Sub-Agent Threads Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ACTIVE SUB-AGENT THREADS",
                    style = MaterialTheme.typography.labelLarge,
                    color = PhotonicCyan,
                    fontWeight = FontWeight.Bold
                )
                PhotonicBadge(
                    text = "4 THREADS ONLINE",
                    signalColor = OperationalEmerald,
                    icon = Icons.Default.Memory
                )
            }
        }

        items(subAgents, key = { it.id }) { agent ->
            SubAgentThreadCard(agent = agent)
        }

        // 512-bit Post Quantum Key Summary Card
        item {
            QuantumGlassCard(
                borderColor = OperationalEmerald.copy(alpha = 0.5f),
                backgroundColor = SpaceCobaltGlassElevated,
                onClick = { viewModel.setEnclaveOverlayVisible(true) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.VpnKey,
                            contentDescription = "PQ Key",
                            tint = OperationalEmeraldLight,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "512-BIT POST-QUANTUM KEY ENCLAVE",
                                style = MaterialTheme.typography.labelMedium,
                                color = OperationalEmeraldLight,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = enclaveKey.keyId,
                                style = MaterialTheme.typography.labelSmall,
                                color = AmbientWhite
                            )
                        }
                    }

                    PhotonicBadge(
                        text = "VIEW HUD OVERLAY",
                        signalColor = OperationalEmerald,
                        icon = Icons.Default.DashboardCustomize
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "SLOT: ${enclaveKey.hardwareSlot}",
                        style = MaterialTheme.typography.labelSmall,
                        color = AmbientWhiteMuted
                    )
                    Text(
                        text = "RE-KEY: ${enclaveKey.rotationRemainingSec}s",
                        style = MaterialTheme.typography.labelSmall,
                        color = OperationalEmerald
                    )
                }
            }
        }
    }
}
