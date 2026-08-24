package com.example.model

enum class AgentStatus(val label: String) {
    ACTIVE("Active / Harmonized"),
    SECURING("Enclave Securing"),
    ISOLATED("Hardware Isolated"),
    SCANNING("Deep Threat Scan")
}

enum class ThreatSeverity(val label: String) {
    CRITICAL("CRITICAL TIER-0"),
    HIGH("HIGH TIER-1"),
    MEDIUM("MEDIUM TIER-2"),
    LOW("LOW TIER-3")
}

enum class ThreatStatus(val label: String) {
    CONTAINED("Photonic Contained"),
    ISOLATING("Enclave Isolating"),
    MITIGATED("Zero-Trust Purged")
}

data class SubAgentThread(
    val id: String,
    val name: String,
    val role: String,
    val status: AgentStatus,
    val neuralLoad: Float, // 0.0f to 1.0f
    val latencyMs: Int,
    val handledTasks: Int,
    val memoryAllocation: String,
    val cryptographicSignature: String
)

data class BiometricState(
    val neuralPulseBpm: Int = 78,
    val galvanicConductance: Float = 4.2f, // uS
    val neuralSyncRatio: Float = 0.984f, // 98.4%
    val enclaveIsolation: String = "Dual Isolation (HW Mem + Sandboxing)",
    val entropyRateKbps: Float = 512.4f,
    val sessionTokenHash: String = "0x9F4C...B82A"
)

data class ArchitectureComparison(
    val layerName: String,
    val year2024Base: String,
    val year2045Quantum: String,
    val securityBenefit: String
)

data class AgisLayerData(
    val layerId: Int,
    val name: String,
    val timestamp: String,
    val subtitle: String,
    val coreRule: String,
    val signals: List<Pair<String, String>>,
    val components: List<String>,
    val specs: List<Pair<String, String>>
)

data class ValidationProof(
    val id: String,
    val name: String,
    val description: String,
    val status: String,
    val verificationDigest: String,
    val isPassing: Boolean = true
)

data class EnclaveKeyInfo(
    val keyId: String,
    val algorithm: String = "Kyber-1024 / Dilithium-5 (512-bit)",
    val hardwareSlot: String = "eUICC Enclave Core #04",
    val memoryAddress: String = "0x7FFF_8000_9000_PQE",
    val rotationRemainingSec: Int = 42,
    val activeState: String = "SEALED_HARDWARE_BOUND"
)
