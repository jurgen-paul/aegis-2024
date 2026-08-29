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

enum class EnclaveLockState(val label: String) {
    LOCKED("Hardware Locked (Biometric Auth Required)"),
    AUTHENTICATING("Verifying Credential Manager Token..."),
    UNLOCKED("Decrypted & Biometrically Attested"),
    DENIED("Authentication Challenge Failed")
}

data class BiometricAttestationDetails(
    val credentialType: String = "Passkey / FIDO2 Biometric",
    val attestationToken: String = "0x8F92...D04E",
    val biometricStrength: String = "Class 3 (Strong Hardware Biometrics)",
    val hardwareSecurityModule: String = "ARM TrustZone / StrongBox Keymaster",
    val verifiedTimestamp: Long = System.currentTimeMillis()
)

data class EnclaveKeyInfo(
    val keyId: String,
    val algorithm: String = "Kyber-1024 / Dilithium-5 (512-bit)",
    val hardwareSlot: String = "eUICC Enclave Core #04",
    val memoryAddress: String = "0x7FFF_8000_9000_PQE",
    val rotationRemainingSec: Int = 42,
    val activeState: String = "SEALED_HARDWARE_BOUND",
    val lockState: EnclaveLockState = EnclaveLockState.LOCKED,
    val attestationDetails: BiometricAttestationDetails? = null
)

enum class IntentRiskLevel(val label: String) {
    SAFE("Low Risk / Autonomous"),
    ELEVATED("Elevated / Sandboxed"),
    RESTRICTED("Restricted / Gated"),
    ISOLATED("Quarantine Contained")
}

data class NeuralIntentPattern(
    val id: String,
    val timestamp: Long = System.currentTimeMillis(),
    val sourceNode: String,
    val targetNode: String,
    val intentType: String,
    val classification: String,
    val confidenceScore: Float, // 0.0 to 1.0
    val entropyDelta: Float,
    val latencyMs: Int,
    val riskLevel: IntentRiskLevel,
    val synchronicHash: String,
    val activeState: String = "ROUTED_VERIFIED"
)

data class NeuralTopologyNode(
    val nodeId: String,
    val label: String,
    val role: String,
    val normalizedX: Float, // 0.0 to 1.0 on canvas
    val normalizedY: Float, // 0.0 to 1.0 on canvas
    val activeTrafficRate: Float, // ops/sec
    val isPrimaryCore: Boolean = false
)

data class CyberNode(
    val id: String,
    val name: String,
    val shortLabel: String,
    val tierNumber: Int,
    val tierLabel: String,
    val description: String,
    val normalizedX: Float,
    val normalizedY: Float,
    val securityProtocol: String,
    val latencyNs: Int,
    val activeLoad: Float, // 0.0f to 1.0f
    val isHardwareEnclave: Boolean = false,
    val activePackets: Int = 0
)

data class CyberNodeRoute(
    val id: String,
    val name: String,
    val intentType: String,
    val description: String,
    val nodeHops: List<String>,
    val riskLevel: IntentRiskLevel,
    val latencyMs: Int,
    val cryptographicDigest: String,
    val isSimulated: Boolean = false
)

enum class PolicyEnforcementLevel(val label: String) {
    STRICT("Zero-Bypass Strict (Hardware Enclave Sealed)"),
    BALANCED("Adaptive Zero-Trust (Attestation Monitored)"),
    DEVELOPMENT("Sandbox Permissive (Full Telemetry Logged)")
}

data class SecurityPolicyRule(
    val id: String,
    val name: String,
    val category: String, // "NEURAL_GATE", "ENCLAVE_CRYPTO", "TELEMETRY_PRIVACY", "CROSS_DOMAIN"
    val description: String,
    val isEnabled: Boolean = true,
    val minimumTier: Int = 1,
    val requiresBiometricConfirmation: Boolean = false,
    val enforcementAction: String
)

data class TelemetryThroughputPoint(
    val timestamp: Long = System.currentTimeMillis(),
    val rawThroughputKbps: Float,
    val sanitizedThroughputKbps: Float,
    val packetsPerSec: Int,
    val piiScrubbedRate: Int,
    val threatAnomalyScore: Float, // 0.0 to 1.0
    val differentialEpsilon: Float = 0.5f
)

data class ThreatCategoryMetric(
    val categoryName: String,
    val shortCode: String,
    val incidentCount: Int,
    val severityLevel: ThreatSeverity,
    val riskRatio: Float, // 0.0 to 1.0
    val accentColorHex: String = "#10B981"
)

