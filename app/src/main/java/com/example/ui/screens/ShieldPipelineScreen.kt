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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ThreatIncidentEntity
import com.example.ui.animation.QuantumVolumetricButton
import com.example.ui.components.PhotonicBadge
import com.example.ui.components.QuantumGlassCard
import com.example.ui.theme.*
import com.example.viewmodel.AgisViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ShieldPipelineScreen(
    viewModel: AgisViewModel
) {
    val threatIncidents by viewModel.threatIncidents.collectAsState()
    val globalThreatLevel by viewModel.globalThreatLevel.collectAsState()

    val pipelineSteps = listOf(
        Triple("1", "Continuous Authentication", "Biometric + Hardware Token (Real-time verification)"),
        Triple("2", "Threat Scanning", "Heuristic Anomaly Detection & Prompt Injection Defense"),
        Triple("3", "Data Sanitization", "Differential Privacy (ε=0.5) & Telemetry Leak Stripping"),
        Triple("4", "Provenance Tracing", "Immutable Cryptographic Chain-of-Custody Signatures"),
        Triple("5", "Audit Logging", "512-bit PQ Sealed Hardware Enclave Ledger")
    )

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
                            text = "LAYER 4 • SHIELD PROTECTION PATH",
                            style = MaterialTheme.typography.labelMedium,
                            color = PhotonicCyanLight
                        )
                        Text(
                            text = "5-Stage Zero-Trust Defense Pipeline",
                            style = MaterialTheme.typography.titleMedium,
                            color = AmbientWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    PhotonicBadge(
                        text = "HARDENED",
                        signalColor = OperationalEmerald,
                        icon = Icons.Default.Security
                    )
                }
            }
        }

        // 5-Stage Visual Pipeline
        item {
            QuantumGlassCard(
                borderColor = PhotonicCyan.copy(alpha = 0.4f),
                backgroundColor = SpaceCobaltGlassElevated
            ) {
                Text(
                    text = "CONTINUOUS ZERO-TRUST PIPELINE STAGES",
                    style = MaterialTheme.typography.labelSmall,
                    color = PhotonicCyanLight,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    pipelineSteps.forEach { (stepNum, title, description) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SpaceCobaltSurface)
                                .border(1.dp, SpaceCobaltGlassBorder, RoundedCornerShape(8.dp))
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(PhotonicCyan.copy(alpha = 0.2f))
                                    .border(1.dp, PhotonicCyan, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stepNum,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = PhotonicCyan,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = AmbientWhite,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AmbientWhiteMuted
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = OperationalEmerald,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // Threat Injection Simulator Lab
        item {
            QuantumGlassCard(
                borderColor = ContainmentCrimson.copy(alpha = 0.4f),
                backgroundColor = SpaceCobaltGlassElevated
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "THREAT INJECTION SIMULATOR LAB",
                            style = MaterialTheme.typography.labelSmall,
                            color = ContainmentCrimson,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Test instant Photonic Crimson Quarantining",
                            style = MaterialTheme.typography.bodySmall,
                            color = AmbientWhiteMuted
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.BugReport,
                        contentDescription = "Threat Simulator",
                        tint = ContainmentCrimson,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        "PROMPT_INJECTION" to ("Prompt Injection Vector" to "Injects jailbreak attempting to dump 512-bit Kyber key"),
                        "EXFILTRATION_PROBE" to ("Exfiltration Probe" to "Simulates unauthorized HTTP/2 telemetry exfiltration attempt"),
                        "TAINTED_MEMORY" to ("Memory Taint / Buffer Overflow" to "Attempts buffer overflow on enclave memory address 0x7FFF8000"),
                        "CROSS_DOMAIN_BYPASS" to ("Cross-Domain Escalation" to "Attempts to bypass explicit neural confirmation gate")
                    ).forEach { (threatKey, details) ->
                        val (threatTitle, threatDesc) = details
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(SpaceCobaltDark)
                                .border(1.dp, ContainmentCrimson.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .clickable {
                                    viewModel.injectSimulatedThreat(threatKey, "untrusted.external.attacker")
                                }
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = threatTitle,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AmbientWhite,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = threatDesc,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AmbientWhiteSubtle
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                QuantumVolumetricButton(
                                    text = "INJECT",
                                    icon = Icons.Default.BugReport,
                                    primaryColor = ContainmentCrimson,
                                    secondaryColor = SolarAmber,
                                    containerColor = ContainmentCrimsonDark.copy(alpha = 0.6f),
                                    shapeRadius = 8.dp,
                                    onClick = {
                                        viewModel.injectSimulatedThreat(threatKey, "untrusted.external.attacker")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quarantined Threat Incidents Log
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "QUARANTINED THREAT INCIDENTS",
                    style = MaterialTheme.typography.labelLarge,
                    color = PhotonicCyan,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${threatIncidents.size} INCIDENTS",
                    style = MaterialTheme.typography.labelSmall,
                    color = AmbientWhiteSubtle
                )
            }
        }

        if (threatIncidents.isEmpty()) {
            item {
                QuantumGlassCard(
                    borderColor = SpaceCobaltGlassBorder,
                    backgroundColor = SpaceCobaltGlass
                ) {
                    Text(
                        text = "Shield posture 100% operational. Zero active threats detected. Inject a threat above to test real-time photonic containment.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AmbientWhiteMuted
                    )
                }
            }
        }

        items(threatIncidents, key = { it.id }) { incident ->
            ThreatIncidentItemCard(incident)
        }
    }
}

@Composable
fun ThreatIncidentItemCard(incident: ThreatIncidentEntity) {
    val dateStr = remember(incident.timestamp) {
        SimpleDateFormat("HH:mm:ss", Locale.US).format(Date(incident.timestamp))
    }

    QuantumGlassCard(
        borderColor = if (incident.status.contains("Contained")) ContainmentCrimson else OperationalEmerald,
        backgroundColor = SpaceCobaltGlassElevated
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = if (incident.status.contains("Contained")) ContainmentCrimson else OperationalEmerald,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${incident.threatType} • $dateStr",
                    style = MaterialTheme.typography.labelSmall,
                    color = PhotonicCyanLight,
                    fontWeight = FontWeight.Bold
                )
            }

            PhotonicBadge(
                text = incident.status,
                signalColor = if (incident.status.contains("Contained")) ContainmentCrimson else OperationalEmerald
            )
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "PAYLOAD: ${incident.payloadSnippet}",
            style = MaterialTheme.typography.bodySmall,
            color = AmbientWhite
        )

        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(SpaceCobaltDark)
                .padding(8.dp)
        ) {
            Text(
                text = "ACTION: ${incident.containmentAction}",
                style = MaterialTheme.typography.labelSmall,
                color = OperationalEmerald
            )
        }
    }
}
