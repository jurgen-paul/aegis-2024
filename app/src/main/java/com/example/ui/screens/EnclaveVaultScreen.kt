package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.data.AuditLogEntity
import com.example.ui.components.PhotonicBadge
import com.example.ui.components.QuantumGlassCard
import com.example.ui.theme.*
import com.example.viewmodel.AgisViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EnclaveVaultScreen(
    viewModel: AgisViewModel
) {
    val enclaveKey by viewModel.enclaveKey.collectAsState()
    val auditLogs by viewModel.auditLogs.collectAsState()
    var selectedFilter by remember { mutableStateOf("ALL") }

    val filteredLogs = remember(auditLogs, selectedFilter) {
        if (selectedFilter == "ALL") auditLogs
        else auditLogs.filter { it.securityTier.contains(selectedFilter, ignoreCase = true) || it.eventType.contains(selectedFilter, ignoreCase = true) }
    }

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
                borderColor = QuantumViolet.copy(alpha = 0.4f),
                backgroundColor = SpaceCobaltCard
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "LAYER 5 • POST-QUANTUM ENCLAVE",
                            style = MaterialTheme.typography.labelMedium,
                            color = QuantumVioletLight
                        )
                        Text(
                            text = "512-bit PQ Dynamic Key Vault",
                            style = MaterialTheme.typography.titleMedium,
                            color = AmbientWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    PhotonicBadge(
                        text = "DUAL ISOLATED",
                        signalColor = QuantumVioletLight,
                        icon = Icons.Default.EnhancedEncryption
                    )
                }
            }
        }

        // 512-bit Dynamic Key Card
        item {
            QuantumGlassCard(
                borderColor = QuantumViolet.copy(alpha = 0.5f),
                backgroundColor = SpaceCobaltGlassElevated
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.VpnKey,
                            contentDescription = "Post Quantum Key",
                            tint = QuantumVioletLight,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "CURRENT POST-QUANTUM KEY",
                                style = MaterialTheme.typography.labelSmall,
                                color = QuantumVioletLight,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = enclaveKey.keyId,
                                style = MaterialTheme.typography.titleSmall,
                                color = AmbientWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Button(
                        onClick = { viewModel.rotateEnclaveKey() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = QuantumViolet,
                            contentColor = AmbientWhite
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "ROTATE",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Key Specifications Grid
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(
                        "LATTICE ALGORITHM" to enclaveKey.algorithm,
                        "HARDWARE SLOT" to enclaveKey.hardwareSlot,
                        "PHYSICAL MEMORY ADDR" to enclaveKey.memoryAddress,
                        "EPHEMERAL ROTATION" to "${enclaveKey.rotationRemainingSec}s remaining",
                        "ENCLAVE STATE" to enclaveKey.activeState
                    ).forEach { (label, value) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(SpaceCobaltDark)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                color = AmbientWhiteMuted
                            )
                            Text(
                                text = value,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (label.contains("ROTATION")) SolarAmber else PhotonicCyanLight,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Dual Isolation Model Architecture Visualizer
        item {
            QuantumGlassCard(
                borderColor = PhotonicCyan.copy(alpha = 0.3f),
                backgroundColor = SpaceCobaltGlassElevated
            ) {
                Text(
                    text = "DUAL ISOLATION ENCLAVE ARCHITECTURE",
                    style = MaterialTheme.typography.labelSmall,
                    color = PhotonicCyanLight,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SpaceCobaltSurface)
                            .border(1.dp, OperationalEmerald.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Memory,
                                    contentDescription = null,
                                    tint = OperationalEmerald,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "ISOLATION 1",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = OperationalEmerald,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Hardware Memory Encryption",
                                style = MaterialTheme.typography.bodySmall,
                                color = AmbientWhite,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Non-exportable secure enclave core registers.",
                                style = MaterialTheme.typography.labelSmall,
                                color = AmbientWhiteMuted
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SpaceCobaltSurface)
                            .border(1.dp, QuantumViolet.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Layers,
                                    contentDescription = null,
                                    tint = QuantumViolet,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "ISOLATION 2",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = QuantumVioletLight,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "OS Kernel Sandboxing",
                                style = MaterialTheme.typography.bodySmall,
                                color = AmbientWhite,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Zero-trust privilege gates preventing cross-app snooping.",
                                style = MaterialTheme.typography.labelSmall,
                                color = AmbientWhiteMuted
                            )
                        }
                    }
                }
            }
        }

        // Room Database Immutable Audit Logs Explorer
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SEALED IMMUTABLE AUDIT LEDGER",
                    style = MaterialTheme.typography.labelLarge,
                    color = PhotonicCyan,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "CLEAR",
                    style = MaterialTheme.typography.labelSmall,
                    color = ContainmentCrimson,
                    modifier = Modifier.clickable { viewModel.clearAuditLedger() }
                )
            }
        }

        // Filter Pills
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("ALL", "TIER-0", "TIER-1", "ENCLAVE").forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) PhotonicCyan.copy(alpha = 0.25f) else SpaceCobaltGlass)
                            .border(
                                1.dp,
                                if (isSelected) PhotonicCyan else SpaceCobaltGlassBorder,
                                RoundedCornerShape(6.dp)
                            )
                            .clickable { selectedFilter = filter }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = filter,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) PhotonicCyan else AmbientWhiteMuted,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (filteredLogs.isEmpty()) {
            item {
                QuantumGlassCard(
                    borderColor = SpaceCobaltGlassBorder,
                    backgroundColor = SpaceCobaltGlass
                ) {
                    Text(
                        text = "No audit log entries for selected filter.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AmbientWhiteMuted
                    )
                }
            }
        }

        items(filteredLogs, key = { it.id }) { log ->
            AuditLogItemCard(log)
        }
    }
}

@Composable
fun AuditLogItemCard(log: AuditLogEntity) {
    val dateStr = remember(log.timestamp) {
        SimpleDateFormat("HH:mm:ss", Locale.US).format(Date(log.timestamp))
    }

    QuantumGlassCard(
        borderColor = if (log.eventType.contains("THREAT")) ContainmentCrimson.copy(alpha = 0.4f)
        else if (log.eventType.contains("ENCLAVE")) QuantumViolet.copy(alpha = 0.4f)
        else PhotonicCyan.copy(alpha = 0.25f),
        backgroundColor = SpaceCobaltGlassElevated
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${log.eventType} • $dateStr",
                style = MaterialTheme.typography.labelSmall,
                color = PhotonicCyanLight,
                fontWeight = FontWeight.Bold
            )

            PhotonicBadge(
                text = log.subAgentId,
                signalColor = OperationalEmerald
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = log.summary,
            style = MaterialTheme.typography.bodySmall,
            color = AmbientWhite
        )

        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "TIER: ${log.securityTier}",
                style = MaterialTheme.typography.labelSmall,
                color = AmbientWhiteMuted
            )
            Text(
                text = "PROOF: ${log.cryptographicProof.take(20)}...",
                style = MaterialTheme.typography.labelSmall,
                color = SolarAmber
            )
        }
    }
}
