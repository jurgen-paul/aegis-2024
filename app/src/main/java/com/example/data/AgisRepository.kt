package com.example.data

import kotlinx.coroutines.flow.Flow

class AgisRepository(private val dao: AgisDao) {

    val allAuditLogs: Flow<List<AuditLogEntity>> = dao.getAllAuditLogs()
    val allTelemetryPackets: Flow<List<TelemetryPacketEntity>> = dao.getAllTelemetryPackets()
    val allThreatIncidents: Flow<List<ThreatIncidentEntity>> = dao.getAllThreatIncidents()
    val allNeuralCommands: Flow<List<NeuralCommandEntity>> = dao.getAllNeuralCommands()

    suspend fun insertAuditLog(log: AuditLogEntity) = dao.insertAuditLog(log)
    suspend fun clearAuditLogs() = dao.clearAuditLogs()

    suspend fun insertTelemetryPacket(packet: TelemetryPacketEntity) = dao.insertTelemetryPacket(packet)
    suspend fun clearTelemetryPackets() = dao.clearTelemetryPackets()

    suspend fun insertThreatIncident(incident: ThreatIncidentEntity) = dao.insertThreatIncident(incident)
    suspend fun updateThreatStatus(id: Long, status: String) = dao.updateThreatStatus(id, status)
    suspend fun clearThreatIncidents() = dao.clearThreatIncidents()

    suspend fun insertNeuralCommand(command: NeuralCommandEntity) = dao.insertNeuralCommand(command)
}
