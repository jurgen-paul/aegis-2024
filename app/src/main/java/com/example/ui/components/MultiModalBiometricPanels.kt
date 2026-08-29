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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.animation.PhotonicSignalPulseIndicator
import com.example.ui.animation.QuantumVolumetricButton
import com.example.ui.theme.*
import com.example.viewmodel.AgisViewModel
import java.util.*
import kotlin.math.*

/**
 * Multi-Modal Biometric Recognition Modules:
 * - Face Recognition (3D Facial Topology Mesh & Anti-Spoof Liveness)
 * - Voice Recognition (Acoustic Spectrogram & Formant Matching)
 * - ID-Search Registry (Enclave Operator & Subject Lookup)
 * - Shadow Recognition (Volumetric Silhouette & Ambient Occlusion)
 * - Footsteps Recognition (Seismic Ground Vibration & Cadence Rhythm)
 */

@Composable
fun FacialRecognitionCard(
    viewModel: AgisViewModel,
    modifier: Modifier = Modifier
) {
    val activeScan by viewModel.activeFacialScan.collectAsState()
    val isScanning by viewModel.isFacialScanning.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "faceScan")
    val scanLineY by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scanLine"
    )

    QuantumGlassCard(
        borderColor = if (isScanning) PhotonicCyan else SpaceCobaltGlassBorder,
        backgroundColor = SpaceCobaltSurface,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Header
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
                        imageVector = Icons.Default.Face,
                        contentDescription = "Face Recognition",
                        tint = PhotonicCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "3D BIOMETRIC FACIAL TOPOLOGY",
                            style = MaterialTheme.typography.labelSmall,
                            color = PhotonicCyanLight,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "68-Point Mesh & Anti-Spoof Attestation",
                            style = MaterialTheme.typography.bodySmall,
                            color = AmbientWhiteMuted
                        )
                    }
                }

                PhotonicBadge(
                    text = if (isScanning) "SCANNING MESH..." else "ATTESTED 99.2%",
                    signalColor = if (isScanning) SolarAmber else OperationalEmerald,
                    icon = if (isScanning) Icons.Default.Sync else Icons.Default.CheckCircle
                )
            }

            // Facial Mesh Reticle Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SpaceCobaltDark)
                    .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawFacialMeshReticle(scanLineY = scanLineY, isScanning = isScanning)
                }

                // Center Hologram Target Frame
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .border(1.dp, PhotonicCyan.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                )
            }

            // Metrics Row
            activeScan?.let { scan ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    BiometricStatBox(
                        label = "MATCH CONFIDENCE",
                        value = "${(scan.matchConfidence * 100).toInt()}%",
                        accentColor = OperationalEmerald,
                        modifier = Modifier.weight(1f)
                    )
                    BiometricStatBox(
                        label = "LIVENESS SCORE",
                        value = "${(scan.livenessScore * 100).toInt()}%",
                        accentColor = PhotonicCyan,
                        modifier = Modifier.weight(1f)
                    )
                    BiometricStatBox(
                        label = "PUPILLARY DIST",
                        value = "${scan.pupillaryDistanceMm} mm",
                        accentColor = QuantumVioletLight,
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = "Cryptographic Digest: ${scan.biometricVectorDigest}",
                    style = MaterialTheme.typography.labelSmall,
                    color = AmbientWhiteSubtle,
                    fontSize = 10.sp
                )
            }

            QuantumVolumetricButton(
                text = if (isScanning) "Scanning 3D Topology..." else "👤 Trigger Facial Mesh Scan",
                onClick = { viewModel.triggerFacialScan() },
                primaryColor = PhotonicCyan,
                secondaryColor = OperationalEmerald,
                icon = Icons.Default.CameraAlt,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun VoiceprintRecognitionCard(
    viewModel: AgisViewModel,
    modifier: Modifier = Modifier
) {
    val activeScan by viewModel.activeVoiceprintScan.collectAsState()
    val isScanning by viewModel.isVoiceScanning.collectAsState()
    val liveFrequencies by viewModel.liveAudioFrequencies.collectAsState()

    QuantumGlassCard(
        borderColor = if (isScanning) QuantumVioletLight else SpaceCobaltGlassBorder,
        backgroundColor = SpaceCobaltSurface,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Header
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
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Voice Recognition",
                        tint = QuantumVioletLight,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "ACOUSTIC SPECTRAL VOICEPRINT",
                            style = MaterialTheme.typography.labelSmall,
                            color = QuantumVioletLight,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Formant Diarization & Deepfake Synthesis Check",
                            style = MaterialTheme.typography.bodySmall,
                            color = AmbientWhiteMuted
                        )
                    }
                }

                PhotonicBadge(
                    text = if (isScanning) "ANALYZING AUDIO..." else "HUMAN AUTH 99.8%",
                    signalColor = if (isScanning) SolarAmber else OperationalEmerald,
                    icon = Icons.Default.GraphicEq
                )
            }

            // Real-Time Audio Spectrum Bars Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SpaceCobaltDark)
                    .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(10.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val barCount = liveFrequencies.size
                    val barWidth = (size.width / barCount) * 0.7f
                    val spacing = (size.width / barCount) * 0.3f

                    liveFrequencies.forEachIndexed { index, energy ->
                        val barHeight = (size.height * energy).coerceIn(4.dp.toPx(), size.height)
                        val x = index * (barWidth + spacing) + spacing / 2
                        val y = size.height - barHeight

                        drawRoundRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(QuantumVioletLight, PhotonicCyan, OperationalEmerald),
                                startY = y,
                                endY = size.height
                            ),
                            topLeft = Offset(x, y),
                            size = Size(barWidth, barHeight),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx(), 4.dp.toPx())
                        )
                    }
                }
            }

            // Voiceprint Formants Grid
            activeScan?.let { scan ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    BiometricStatBox(label = "PITCH (F0)", value = "${scan.pitchHz.toInt()} Hz", accentColor = PhotonicCyan, modifier = Modifier.weight(1f))
                    BiometricStatBox(label = "FORMANT F1", value = "${scan.formantF1Hz.toInt()} Hz", accentColor = QuantumVioletLight, modifier = Modifier.weight(1f))
                    BiometricStatBox(label = "FORMANT F2", value = "${scan.formantF2Hz.toInt()} Hz", accentColor = OperationalEmerald, modifier = Modifier.weight(1f))
                    BiometricStatBox(label = "SYNTHETIC RISK", value = "${(scan.deepfakeSyntheticScore * 100).toInt()}%", accentColor = if (scan.deepfakeSyntheticScore > 0.2f) ContainmentCrimson else OperationalEmerald, modifier = Modifier.weight(1f))
                }
            }

            QuantumVolumetricButton(
                text = if (isScanning) "Capturing Harmonics..." else "🎙️ Capture Acoustic Voiceprint",
                onClick = { viewModel.triggerVoiceprintScan() },
                primaryColor = QuantumVioletLight,
                secondaryColor = PhotonicCyan,
                icon = Icons.Default.MicNone,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun IdSearchRegistryCard(
    viewModel: AgisViewModel,
    modifier: Modifier = Modifier
) {
    val subjects by viewModel.subjectRegistry.collectAsState()
    val searchQuery by viewModel.idSearchQuery.collectAsState()
    val clearanceFilter by viewModel.idSelectedClearanceFilter.collectAsState()
    val threatFilter by viewModel.idSelectedThreatFilter.collectAsState()

    val filteredSubjects = subjects.filter { sub ->
        val matchesQuery = searchQuery.isBlank() ||
                sub.fullName.contains(searchQuery, ignoreCase = true) ||
                sub.operativeCode.contains(searchQuery, ignoreCase = true) ||
                sub.id.contains(searchQuery, ignoreCase = true)

        val matchesClearance = clearanceFilter == "ALL" || clearanceFilter == null || sub.clearanceLevel.contains(clearanceFilter!!, ignoreCase = true)
        val matchesThreat = threatFilter == null || sub.threatRating == threatFilter

        matchesQuery && matchesClearance && matchesThreat
    }

    QuantumGlassCard(
        borderColor = PhotonicCyan.copy(alpha = 0.4f),
        backgroundColor = SpaceCobaltSurface,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Header
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
                        imageVector = Icons.Default.Badge,
                        contentDescription = "ID Search",
                        tint = PhotonicCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "ENCLAVE ID-SEARCH & CREDENTIAL REGISTRY",
                            style = MaterialTheme.typography.labelSmall,
                            color = PhotonicCyanLight,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Searchable Operative Dossiers & Black-Notice Lookup",
                            style = MaterialTheme.typography.bodySmall,
                            color = AmbientWhiteMuted
                        )
                    }
                }

                PhotonicBadge(
                    text = "${filteredSubjects.size} / ${subjects.size} MATCHES",
                    signalColor = PhotonicCyan
                )
            }

            // Search Bar Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateIdSearchQuery(it) },
                placeholder = {
                    Text(
                        text = "Search by ID, Operative Code, Name, or Black Hash...",
                        style = MaterialTheme.typography.bodySmall,
                        color = AmbientWhiteMuted
                    )
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = PhotonicCyan)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.updateIdSearchQuery("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = AmbientWhiteMuted)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PhotonicCyan,
                    unfocusedBorderColor = SpaceCobaltGlassBorder,
                    focusedTextColor = AmbientWhite,
                    unfocusedTextColor = AmbientWhite,
                    cursorColor = PhotonicCyan
                ),
                singleLine = true
            )

            // Clearance Filter Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("ALL" to "All Tiers", "TIER-6" to "Tier-6 Master", "TIER-4" to "Tier-4", "RED_FLAG" to "Red Notice").forEach { (code, label) ->
                    val isSelected = clearanceFilter == code
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) PhotonicCyan.copy(alpha = 0.25f) else SpaceCobaltGlassElevated)
                            .border(1.dp, if (isSelected) PhotonicCyan else SpaceCobaltGlassBorder, RoundedCornerShape(6.dp))
                            .clickable { viewModel.setIdClearanceFilter(if (isSelected && code != "ALL") "ALL" else code) }
                            .padding(vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) PhotonicCyan else AmbientWhiteMuted,
                            fontSize = 9.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            // Subject Dossier Cards
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                filteredSubjects.forEach { sub ->
                    SubjectDossierItem(
                        subject = sub,
                        onTriggerAttestation = { viewModel.runComprehensiveMultiModalAttestation(sub.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SubjectDossierItem(
    subject: SubjectIdentity,
    onTriggerAttestation: () -> Unit
) {
    val cardBorder = if (subject.isRedNotice) ContainmentCrimson else SpaceCobaltGlassBorder
    val cardBg = if (subject.isRedNotice) ContainmentCrimsonDark.copy(alpha = 0.2f) else SpaceCobaltGlassElevated

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(cardBg)
            .border(1.dp, cardBorder, RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(if (subject.isRedNotice) ContainmentCrimson.copy(alpha = 0.3f) else PhotonicCyan.copy(alpha = 0.2f))
                            .border(1.dp, if (subject.isRedNotice) ContainmentCrimson else PhotonicCyan, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (subject.isRedNotice) Icons.Default.Warning else Icons.Default.Person,
                            contentDescription = null,
                            tint = if (subject.isRedNotice) ContainmentCrimson else PhotonicCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column {
                        Text(
                            text = subject.fullName,
                            style = MaterialTheme.typography.titleSmall,
                            color = AmbientWhite,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${subject.operativeCode} • ${subject.clearanceLevel}",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (subject.isRedNotice) ContainmentCrimsonLight else PhotonicCyanLight,
                            fontSize = 10.sp
                        )
                    }
                }

                PhotonicBadge(
                    text = if (subject.isRedNotice) "RED NOTICE" else "AUTHORIZED",
                    signalColor = if (subject.isRedNotice) ContainmentCrimson else OperationalEmerald
                )
            }

            Text(
                text = "Affiliation: ${subject.affiliation}",
                style = MaterialTheme.typography.bodySmall,
                color = AmbientWhiteMuted,
                fontSize = 11.sp
            )

            // Biometric vectors preview
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Face: ${(subject.facialConfidence * 100).toInt()}% • Voice: ${(subject.voiceConfidence * 100).toInt()}% • Shadow: ${(subject.shadowSilhouetteScore * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = AmbientWhiteSubtle,
                    fontSize = 10.sp
                )
                Text(
                    text = "Lock 5-Vector ▶",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (subject.isRedNotice) ContainmentCrimson else PhotonicCyan,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onTriggerAttestation() }
                )
            }
        }
    }
}

@Composable
fun ShadowSilhouetteCard(
    viewModel: AgisViewModel,
    modifier: Modifier = Modifier
) {
    val activeScan by viewModel.activeShadowScan.collectAsState()
    val isScanning by viewModel.isShadowScanning.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "shadowPhase")
    val shadowAngle by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shadowAngle"
    )

    QuantumGlassCard(
        borderColor = if (isScanning) SolarAmber else SpaceCobaltGlassBorder,
        backgroundColor = SpaceCobaltSurface,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Header
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
                        imageVector = Icons.Default.Accessibility,
                        contentDescription = "Shadow Recognition",
                        tint = SolarAmber,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "VOLUMETRIC SHADOW & SILHOUETTE PROFILING",
                            style = MaterialTheme.typography.labelSmall,
                            color = SolarAmber,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Ambient Light Occlusion & Geometric Height Profiling",
                            style = MaterialTheme.typography.bodySmall,
                            color = AmbientWhiteMuted
                        )
                    }
                }

                PhotonicBadge(
                    text = if (isScanning) "CALCULATING RAYCAST..." else "PROFILE 96.5%",
                    signalColor = if (isScanning) SolarAmber else OperationalEmerald
                )
            }

            // Shadow Silhouette Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SpaceCobaltDark)
                    .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawShadowSilhouette(shadowAngle = shadowAngle, isScanning = isScanning)
                }
            }

            activeScan?.let { scan ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    BiometricStatBox(label = "EST. HEIGHT", value = "${scan.estimatedHeightCm} cm", accentColor = SolarAmber, modifier = Modifier.weight(1f))
                    BiometricStatBox(label = "SHOULDER/HIP", value = "${scan.shoulderToHipRatio}", accentColor = PhotonicCyan, modifier = Modifier.weight(1f))
                    BiometricStatBox(label = "GAIT SYMMETRY", value = "${(scan.volumetricGaitSymmetry * 100).toInt()}%", accentColor = OperationalEmerald, modifier = Modifier.weight(1f))
                    BiometricStatBox(label = "AMBIENT LUX", value = "${scan.ambientOcclusionLux.toInt()} lx", accentColor = QuantumVioletLight, modifier = Modifier.weight(1f))
                }
            }

            QuantumVolumetricButton(
                text = if (isScanning) "Raycasting Silhouette..." else "👥 Scan Volumetric Shadow",
                onClick = { viewModel.triggerShadowScan() },
                primaryColor = SolarAmber,
                secondaryColor = QuantumVioletLight,
                icon = Icons.Default.FilterFrames,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun FootstepsGaitCard(
    viewModel: AgisViewModel,
    modifier: Modifier = Modifier
) {
    val activeScan by viewModel.activeFootstepsScan.collectAsState()
    val isScanning by viewModel.isFootstepsScanning.collectAsState()
    val liveSeismic by viewModel.liveSeismicWaveform.collectAsState()

    QuantumGlassCard(
        borderColor = if (isScanning) OperationalEmerald else SpaceCobaltGlassBorder,
        backgroundColor = SpaceCobaltSurface,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Header
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
                        imageVector = Icons.Default.DirectionsWalk,
                        contentDescription = "Footsteps Recognition",
                        tint = OperationalEmerald,
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "SEISMIC GEOPHONE FOOTSTEPS RECOGNITION",
                            style = MaterialTheme.typography.labelSmall,
                            color = OperationalEmeraldLight,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Ground Vibration Harmonic Cadence & Force Impulse",
                            style = MaterialTheme.typography.bodySmall,
                            color = AmbientWhiteMuted
                        )
                    }
                }

                PhotonicBadge(
                    text = if (isScanning) "SEISMIC LISTENING..." else "CADENCE LOCKED",
                    signalColor = if (isScanning) SolarAmber else OperationalEmerald
                )
            }

            // Seismic Waveform Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SpaceCobaltDark)
                    .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(10.dp))
                    .padding(8.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawSeismicWaveform(waveform = liveSeismic, isScanning = isScanning)
                }
            }

            activeScan?.let { scan ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    BiometricStatBox(label = "CADENCE (SPM)", value = "${scan.cadenceSpm} SPM", accentColor = OperationalEmerald, modifier = Modifier.weight(1f))
                    BiometricStatBox(label = "STRIKE FORCE", value = "${scan.groundForceNewtons.toInt()} N", accentColor = PhotonicCyan, modifier = Modifier.weight(1f))
                    BiometricStatBox(label = "HEEL/TOE RATIO", value = "${scan.heelToePressureRatio}", accentColor = SolarAmber, modifier = Modifier.weight(1f))
                    BiometricStatBox(label = "RESONANCE", value = "${scan.gaitResonanceHz} Hz", accentColor = QuantumVioletLight, modifier = Modifier.weight(1f))
                }
            }

            QuantumVolumetricButton(
                text = if (isScanning) "Analyzing Seismic Impulse..." else "👣 Listen to Footstep Cadence",
                onClick = { viewModel.triggerFootstepsScan() },
                primaryColor = OperationalEmerald,
                secondaryColor = PhotonicCyan,
                icon = Icons.Default.Sensors,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun BiometricStatBox(
    label: String,
    value: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SpaceCobaltGlassElevated)
            .border(1.dp, accentColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = AmbientWhiteMuted, fontSize = 8.sp)
            Text(text = value, style = MaterialTheme.typography.bodySmall, color = accentColor, fontWeight = FontWeight.Bold)
        }
    }
}

private fun DrawScope.drawFacialMeshReticle(scanLineY: Float, isScanning: Boolean) {
    val cx = size.width / 2f
    val cy = size.height / 2f

    // 1. Draw 3D wireframe oval
    val ovalWidth = 90.dp.toPx()
    val ovalHeight = 120.dp.toPx()
    drawOval(
        color = PhotonicCyan.copy(alpha = 0.5f),
        topLeft = Offset(cx - ovalWidth / 2, cy - ovalHeight / 2),
        size = Size(ovalWidth, ovalHeight),
        style = Stroke(width = 1.5.dp.toPx())
    )

    // 2. Draw 68-point simulated biometric mesh nodes
    val nodeOffsets = listOf(
        // Eyes
        Offset(cx - 24.dp.toPx(), cy - 18.dp.toPx()),
        Offset(cx + 24.dp.toPx(), cy - 18.dp.toPx()),
        // Nose bridge & tip
        Offset(cx, cy - 6.dp.toPx()),
        Offset(cx, cy + 10.dp.toPx()),
        // Mouth
        Offset(cx - 16.dp.toPx(), cy + 28.dp.toPx()),
        Offset(cx + 16.dp.toPx(), cy + 28.dp.toPx()),
        Offset(cx, cy + 34.dp.toPx()),
        // Jawline contour
        Offset(cx - 38.dp.toPx(), cy + 12.dp.toPx()),
        Offset(cx + 38.dp.toPx(), cy + 12.dp.toPx()),
        Offset(cx, cy + 54.dp.toPx())
    )

    nodeOffsets.forEach { node ->
        drawCircle(
            color = if (isScanning) PhotonicCyanLight else OperationalEmerald,
            radius = 3.dp.toPx(),
            center = node
        )
    }

    // Connect mesh triangulation lines
    for (i in 0 until nodeOffsets.size - 1) {
        drawLine(
            color = PhotonicCyan.copy(alpha = 0.35f),
            start = nodeOffsets[i],
            end = nodeOffsets[i + 1],
            strokeWidth = 1.dp.toPx()
        )
    }

    // Scanning laser bar
    if (isScanning) {
        val y = size.height * scanLineY
        drawLine(
            color = PhotonicCyan,
            start = Offset(cx - ovalWidth / 2 - 10.dp.toPx(), y),
            end = Offset(cx + ovalWidth / 2 + 10.dp.toPx(), y),
            strokeWidth = 2.dp.toPx()
        )
    }
}

private fun DrawScope.drawShadowSilhouette(shadowAngle: Float, isScanning: Boolean) {
    val cx = size.width / 2f
    val cy = size.height * 0.75f

    // Draw ground projection shadow plane
    val groundWidth = size.width * 0.8f
    drawLine(
        color = SpaceCobaltGlassBorder,
        start = Offset(cx - groundWidth / 2, cy),
        end = Offset(cx + groundWidth / 2, cy),
        strokeWidth = 1.5.dp.toPx()
    )

    // Draw projected volumetric shadow ellipse
    val rad = Math.toRadians(shadowAngle.toDouble())
    val skewX = (tan(rad) * 30.dp.toPx()).toFloat()

    drawOval(
        brush = Brush.horizontalGradient(
            colors = listOf(SolarAmber.copy(alpha = 0.6f), QuantumVioletLight.copy(alpha = 0.3f), Color.Transparent),
            startX = cx - 50.dp.toPx() + skewX,
            endX = cx + 80.dp.toPx() + skewX
        ),
        topLeft = Offset(cx - 50.dp.toPx() + skewX, cy - 10.dp.toPx()),
        size = Size(100.dp.toPx(), 20.dp.toPx())
    )

    // Standing Silhouette Figure
    val headCenter = Offset(cx, cy - 90.dp.toPx())
    drawCircle(
        color = AmbientWhite.copy(alpha = 0.85f),
        radius = 12.dp.toPx(),
        center = headCenter
    )

    // Torso and Limbs
    drawLine(
        color = AmbientWhite.copy(alpha = 0.85f),
        start = Offset(cx, cy - 78.dp.toPx()),
        end = Offset(cx, cy - 35.dp.toPx()),
        strokeWidth = 4.dp.toPx()
    )
    // Legs
    drawLine(
        color = AmbientWhite.copy(alpha = 0.85f),
        start = Offset(cx, cy - 35.dp.toPx()),
        end = Offset(cx - 16.dp.toPx(), cy),
        strokeWidth = 3.dp.toPx()
    )
    drawLine(
        color = AmbientWhite.copy(alpha = 0.85f),
        start = Offset(cx, cy - 35.dp.toPx()),
        end = Offset(cx + 16.dp.toPx(), cy),
        strokeWidth = 3.dp.toPx()
    )
}

private fun DrawScope.drawSeismicWaveform(waveform: List<Float>, isScanning: Boolean) {
    if (waveform.size < 2) return

    val w = size.width
    val h = size.height
    val stepX = w / (waveform.size - 1)

    // Baseline grid
    drawLine(
        color = SpaceCobaltGlassBorder.copy(alpha = 0.5f),
        start = Offset(0f, h / 2f),
        end = Offset(w, h / 2f),
        strokeWidth = 1.dp.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f)
    )

    val path = Path()
    waveform.forEachIndexed { index, amp ->
        val x = index * stepX
        val y = h / 2f - (amp - 0.5f) * (h * 0.8f)
        if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }

    drawPath(
        path = path,
        brush = Brush.horizontalGradient(
            colors = listOf(PhotonicCyan, OperationalEmerald, OperationalEmeraldLight),
            startX = 0f,
            endX = w
        ),
        style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
}
