package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.AgisViewModel

/**
 * Full-featured Multi-Modal Biometric Reconnaissance & Surveillance Screen:
 * - 360° Quantum Radar Track & Trace
 * - 3D Facial Topology Mesh Recognition
 * - Acoustic Spectral Voiceprint Recognition
 * - Post-Quantum ID-Search Credential Registry
 * - Volumetric Shadow & Silhouette Profiling
 * - Seismic Geophone Footsteps Cadence Recognition
 */
@Composable
fun BiometricRadarScreen(
    viewModel: AgisViewModel,
    modifier: Modifier = Modifier
) {
    var activeSubMode by remember { mutableStateOf("ALL") } // "ALL", "RADAR", "FACE", "VOICE", "ID_SEARCH", "SHADOW", "FOOTSTEPS"

    val subModes = listOf(
        SubModeItem("ALL", "All Vectors", Icons.Default.AllInclusive),
        SubModeItem("RADAR", "360° Radar", Icons.Default.Radar),
        SubModeItem("FACE", "Face 3D", Icons.Default.Face),
        SubModeItem("VOICE", "Voiceprint", Icons.Default.Mic),
        SubModeItem("ID_SEARCH", "ID Search", Icons.Default.Badge),
        SubModeItem("SHADOW", "Shadow", Icons.Default.Accessibility),
        SubModeItem("FOOTSTEPS", "Footsteps", Icons.Default.DirectionsWalk)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp)
    ) {
        // Top Banner / Switcher
        item {
            QuantumGlassCard(
                borderColor = PhotonicCyan.copy(alpha = 0.5f),
                backgroundColor = SpaceCobaltSurface
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                                imageVector = Icons.Default.TrackChanges,
                                contentDescription = "Surveillance Hub",
                                tint = PhotonicCyan,
                                modifier = Modifier.size(24.dp)
                            )
                            Column {
                                Text(
                                    text = "MULTI-MODAL RECON & RECOGNITION MATRIX",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PhotonicCyanLight,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Radar Track & 5-Vector Biometric Sentinel",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = AmbientWhite,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        PhotonicBadge(
                            text = "QUANTUM SENSORY",
                            signalColor = OperationalEmerald,
                            icon = Icons.Default.Sensors
                        )
                    }

                    // Horizontal Sub-Mode Navigation Chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(subModes) { mode ->
                            val isSelected = activeSubMode == mode.id
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) PhotonicCyan.copy(alpha = 0.25f) else SpaceCobaltGlassElevated)
                                    .border(1.dp, if (isSelected) PhotonicCyan else SpaceCobaltGlassBorder, RoundedCornerShape(8.dp))
                                    .clickable { activeSubMode = mode.id }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = mode.icon,
                                        contentDescription = null,
                                        tint = if (isSelected) PhotonicCyan else AmbientWhiteMuted,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = mode.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isSelected) PhotonicCyan else AmbientWhiteMuted,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Sub-Mode Views
        when (activeSubMode) {
            "ALL" -> {
                item { RadarTrackAndTraceCard(viewModel = viewModel) }
                item { FacialRecognitionCard(viewModel = viewModel) }
                item { VoiceprintRecognitionCard(viewModel = viewModel) }
                item { IdSearchRegistryCard(viewModel = viewModel) }
                item { ShadowSilhouetteCard(viewModel = viewModel) }
                item { FootstepsGaitCard(viewModel = viewModel) }
            }
            "RADAR" -> {
                item { RadarTrackAndTraceCard(viewModel = viewModel) }
                item { IdSearchRegistryCard(viewModel = viewModel) }
            }
            "FACE" -> {
                item { FacialRecognitionCard(viewModel = viewModel) }
                item { IdSearchRegistryCard(viewModel = viewModel) }
            }
            "VOICE" -> {
                item { VoiceprintRecognitionCard(viewModel = viewModel) }
                item { IdSearchRegistryCard(viewModel = viewModel) }
            }
            "ID_SEARCH" -> {
                item { IdSearchRegistryCard(viewModel = viewModel) }
                item { RadarTrackAndTraceCard(viewModel = viewModel) }
            }
            "SHADOW" -> {
                item { ShadowSilhouetteCard(viewModel = viewModel) }
                item { FacialRecognitionCard(viewModel = viewModel) }
            }
            "FOOTSTEPS" -> {
                item { FootstepsGaitCard(viewModel = viewModel) }
                item { VoiceprintRecognitionCard(viewModel = viewModel) }
            }
        }
    }
}

private data class SubModeItem(
    val id: String,
    val label: String,
    val icon: ImageVector
)
