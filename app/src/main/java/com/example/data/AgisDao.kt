package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AgisDao {

    // Audit Logs
    @Query("SELECT * FROM audit_logs ORDER BY timestamp DESC LIMIT 100")
    fun getAllAuditLogs(): Flow<List<AuditLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditLog(log: AuditLogEntity): Long

    @Query("DELETE FROM audit_logs")
    suspend fun clearAuditLogs()

    // Telemetry Packets
    @Query("SELECT * FROM telemetry_packets ORDER BY timestamp DESC LIMIT 50")
    fun getAllTelemetryPackets(): Flow<List<TelemetryPacketEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTelemetryPacket(packet: TelemetryPacketEntity): Long

    @Query("DELETE FROM telemetry_packets")
    suspend fun clearTelemetryPackets()

    // Threat Incidents
    @Query("SELECT * FROM threat_incidents ORDER BY timestamp DESC LIMIT 50")
    fun getAllThreatIncidents(): Flow<List<ThreatIncidentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThreatIncident(incident: ThreatIncidentEntity): Long

    @Query("UPDATE threat_incidents SET status = :newStatus WHERE id = :id")
    suspend fun updateThreatStatus(id: Long, newStatus: String)

    @Query("DELETE FROM threat_incidents")
    suspend fun clearThreatIncidents()

    // Neural Commands
    @Query("SELECT * FROM neural_commands ORDER BY timestamp DESC LIMIT 50")
    fun getAllNeuralCommands(): Flow<List<NeuralCommandEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNeuralCommand(command: NeuralCommandEntity): Long
}
