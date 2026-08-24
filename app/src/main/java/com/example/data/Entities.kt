package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val eventType: String,
    val securityTier: String,
    val summary: String,
    val cryptographicProof: String,
    val subAgentId: String
)

@Entity(tableName = "telemetry_packets")
data class TelemetryPacketEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val sourceModule: String,
    val rawPayloadJson: String,
    val sanitizedPayloadJson: String,
    val piiStrippedCount: Int,
    val differentialNoiseEpsilon: Float,
    val perimeterProofStatus: String
)

@Entity(tableName = "threat_incidents")
data class ThreatIncidentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val threatType: String,
    val severity: String,
    val sourceDomain: String,
    val status: String,
    val payloadSnippet: String,
    val containmentAction: String
)

@Entity(tableName = "neural_commands")
data class NeuralCommandEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val prompt: String,
    val targetDomain: String,
    val isCrossDomain: Boolean,
    val neuralConfirmed: Boolean,
    val executionResult: String,
    val latencyMs: Long
)
