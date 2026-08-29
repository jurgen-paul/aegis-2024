package com.example.model

object AgisArchitectureConstants {

    val ARCHITECTURE_LAYERS = listOf(
        AgisLayerData(
            layerId = 1,
            name = "Quantum Glass Visual System",
            timestamp = "0:55",
            subtitle = "Volumetric Translucent Panels & Photonic Signals",
            coreRule = "Spatial depth clarifies neural actions without obscuring the underlying security posture.",
            signals = listOf(
                "Activity" to "Photonic Cyan (#00E5FF)",
                "Primary Content" to "Ambient White (#F0F6FC)",
                "Healthy Status" to "Operational Emerald (#00E676)",
                "Threat Containment" to "Crimson (#FF1744)"
            ),
            components = listOf(
                "Base: Deep Space Cobalt",
                "Translucent Structure: Volumetric Glass Panels",
                "Spatial depth rendering with dynamic refractive indices",
                "Non-occluding security telemetry overlays"
            ),
            specs = listOf(
                "Visual Depth Mode" to "Multi-Layered Volumetric Glass",
                "Backdrop Filter" to "Hardware Gaussian & Photonic Edge Glow",
                "Refraction Index" to "1.42 (Quantum Lattice Emulation)"
            )
        ),
        AgisLayerData(
            layerId = 2,
            name = "Neural Interaction & Intent Routing",
            timestamp = "1:40",
            subtitle = "Real-Time Biometrics & Active Sub-Agent Threads",
            coreRule = "Domain crossing operations require explicit biomorphic and cryptographic neural confirmation.",
            signals = listOf(
                "Biometric Pulse" to "Continuous Electro-Neural Sync",
                "Sub-Agent Threads" to "Active Task Orchestration",
                "Confirmation Gate" to "Zero-Bypass Domain Sentinel",
                "Enclave Status" to "Hardware Isolation Locked"
            ),
            components = listOf(
                "Header: Real-Time Biometrics & Active Sub-Agent Threads",
                "Domain Routing: Explicit Neural Confirmation Required",
                "Status Signals: Continuous Enclave Isolation Indicators",
                "Input Surface: Glass Input Pane (Multi-Modal & Direct Intent)"
            ),
            specs = listOf(
                "Input Modes" to "Direct Intent, Multi-Modal, Neural Token",
                "Confirmation Protocol" to "Biomorphic Haptic + Cryptographic Hash",
                "Thread Isolation" to "Sub-Agent Memory Enclave Partitioning"
            )
        ),
        AgisLayerData(
            layerId = 3,
            name = "Hardened System Architecture",
            timestamp = "2:25",
            subtitle = "Unidirectional Flow: Composery -> VM -> Gates -> Vault -> Boundary",
            coreRule = "Unidirectional, immutable state guarantees deterministic execution across all tiers.",
            signals = listOf(
                "Composery" to "Verified Intent Rendering",
                "State VM" to "Immutable State Vector",
                "Control Gates" to "Hardware Real-Time Policies",
                "Storage Vault" to "Post-Quantum Memory Encryption"
            ),
            components = listOf(
                "Tier 1 [Visual]: Neural Composery",
                "Tier 2 [State]: Active ViewModel",
                "Tier 3 [Control]: Zero-Trust Hardware Filters",
                "Tier 4 [Storage]: Post-Quantum Encrypted Database",
                "Tier 5 [Boundary]: Encrypted Network Interface"
            ),
            specs = listOf(
                "State Pattern" to "Unidirectional Immutable Flow (MVI/MVVM)",
                "Policy Engine" to "Nanosecond-Latency Hardware Filters",
                "Boundary Protocol" to "Continuous Perimeter Leak Proofs"
            )
        ),
        AgisLayerData(
            layerId = 4,
            name = "Shield Protection Path",
            timestamp = "3:25",
            subtitle = "5-Stage Zero-Trust Defense Pipeline",
            coreRule = "Every instruction is continuously authenticated, threat scanned, sanitized, traced, and logged.",
            signals = listOf(
                "Stage 1" to "Continuous Authentication",
                "Stage 2" to "Threat Scanning",
                "Stage 3" to "Data Sanitization",
                "Stage 4" to "Provenance Tracing",
                "Stage 5" to "Audit Logging"
            ),
            components = listOf(
                "Continuous Authentication: Biometric + Hardware Token Check",
                "Threat Scanning: Heuristic & AI Anomaly Containment",
                "Data Sanitization: Telemetry & Token Sanitizer",
                "Provenance Tracing: Cryptographic Chain-of-Custody",
                "Audit Logging: Hardware-Sealed Immutable Ledger"
            ),
            specs = listOf(
                "Pipeline Latency" to "< 2.4 ms per invocation",
                "Containment SLA" to "Instantaneous Photonic Crimson Quarantining",
                "Audit Persistence" to "Hardware-Backed Local Post-Quantum Vault"
            )
        ),
        AgisLayerData(
            layerId = 5,
            name = "Post-Quantum Local Enclave",
            timestamp = "4:30",
            subtitle = "512-bit Dynamic Keys & Dual Isolation",
            coreRule = "Keys never leave non-exportable hardware enclave memory; protected against quantum attacks.",
            signals = listOf(
                "Key Type" to "512-bit Post-Quantum Dynamic Keys",
                "Hardware Backing" to "Secure Enclave Hardware",
                "Isolation Model" to "Dual Isolation (HW Mem + OS Sandboxing)",
                "Lattice Cipher" to "Kyber-1024 / Dilithium-5 Emulation"
            ),
            components = listOf(
                "Key Type: 512-bit Post-Quantum Dynamic Keys",
                "Hardware Backing: Secure Enclave Hardware Core",
                "Isolation Model: Dual Isolation (Hardware Memory Encryption + OS Memory Sandboxing)",
                "Dynamic Key Rotation: Ephemeral Session Lifecycles"
            ),
            specs = listOf(
                "Lattice Dimension" to "512-bit Quantum Resistant Matrix",
                "Enclave Memory" to "Isolated Secure Core Partition",
                "Memory Sandbox" to "Kernel Level Dual-Enclave Barrier"
            )
        ),
        AgisLayerData(
            layerId = 6,
            name = "Continuous Autonomous Validation",
            timestamp = "5:30",
            subtitle = "Deterministic Builds, Migrations & Perimeter Proofs",
            coreRule = "Continuous validation guarantees zero configuration drift and provable perimeter containment.",
            signals = listOf(
                "Check 1" to "Deterministic Builds Verification",
                "Check 2" to "Schema Migration Verification",
                "Check 3" to "Network Perimeter Proofs",
                "Telemetry" to "Real-Time Leak Sanitizer"
            ),
            components = listOf(
                "Pre-Execution Check 1: Deterministic Builds",
                "Pre-Execution Check 2: Migration Verification",
                "Pre-Execution Check 3: Perimeter Proofs",
                "Autonomous Network Telemetry Sanitizer Layer"
            ),
            specs = listOf(
                "Build Reproducibility" to "100% Bit-for-Bit Deterministic Hash",
                "Migration Integrity" to "Zero-Data-Loss Cryptographic Proof",
                "Sanitization Protocol" to "Differential Privacy (ε = 0.5)"
            )
        )
    )

    val COMPARISON_MATRIX = listOf(
        ArchitectureComparison(
            layerName = "Visual Interface",
            year2024Base = "Deep marine blue canvas with static glass overlay",
            year2045Quantum = "Deep space cobalt canvas with dynamic volumetric quantum glass panels",
            securityBenefit = "Visual clarity of neural states and threat posture without obscuring telemetry"
        ),
        ArchitectureComparison(
            layerName = "State & Control",
            year2024Base = "ViewModel defining unidirectional UI state",
            year2045Quantum = "Active ViewModel with real-time biometric and sub-agent thread monitoring",
            securityBenefit = "Real-time state synchronization with biomorphic hardware resonance"
        ),
        ArchitectureComparison(
            layerName = "Input Routing",
            year2024Base = "Razor-sharp glass input surface for multi-modal prompts",
            year2045Quantum = "Integrated command pane with explicit neural confirmation per domain crossing",
            securityBenefit = "Eliminates prompt-injection escalation across sensitive micro-domains"
        ),
        ArchitectureComparison(
            layerName = "Security Gates",
            year2024Base = "Hardened policy filters guarding external API calls",
            year2045Quantum = "Zero-trust hardware filters enforcing real-time policy checks",
            securityBenefit = "Nanosecond hardware-level interception before processing execution"
        ),
        ArchitectureComparison(
            layerName = "Local Storage",
            year2024Base = "32-byte key SQLCipher + AES-GCM via Android Keystore",
            year2045Quantum = "512-bit post-quantum keys bound to hardware enclave memory",
            securityBenefit = "Quantum-resistant encryption protected against future Shor's algorithm attacks"
        ),
        ArchitectureComparison(
            layerName = "External Boundary",
            year2024Base = "Retrofit/HTTP interface to remote Gemini models",
            year2045Quantum = "Encrypted network interface with continuous perimeter leak proofs",
            securityBenefit = "Continuous mathematical proof of zero plaintext or metadata leakage"
        ),
        ArchitectureComparison(
            layerName = "Build & Release",
            year2024Base = "Reproducible builds with manual backup verification",
            year2045Quantum = "Autonomous continuous validation with automated schema migration proofs",
            securityBenefit = "Self-verifying continuous deployment with provable boundary integrity"
        )
    )

    val CYBER_NODES = listOf(
        CyberNode(
            id = "NODE_COMPOSERY",
            name = "Operator Neural Composery",
            shortLabel = "Composery",
            tierNumber = 1,
            tierLabel = "Visual / Intent Surface",
            description = "Volumetric glass input surface capturing multi-modal user intent and biomorphic touch resonance.",
            normalizedX = 0.50f,
            normalizedY = 0.13f,
            securityProtocol = "Biometric Haptic Attestation (FIDO2)",
            latencyNs = 120,
            activeLoad = 0.42f,
            isHardwareEnclave = false,
            activePackets = 14
        ),
        CyberNode(
            id = "NODE_VIEWMODEL",
            name = "State ViewModel Core",
            shortLabel = "State Core",
            tierNumber = 2,
            tierLabel = "Unidirectional MVI State",
            description = "Deterministic unidirectional state engine maintaining immutable state representations across threads.",
            normalizedX = 0.20f,
            normalizedY = 0.38f,
            securityProtocol = "Kotlin StateFlow Mutex Locks",
            latencyNs = 85,
            activeLoad = 0.65f,
            isHardwareEnclave = false,
            activePackets = 28
        ),
        CyberNode(
            id = "NODE_POLICY_GATE",
            name = "Zero-Trust Hardware Gate",
            shortLabel = "Policy Gate",
            tierNumber = 3,
            tierLabel = "Hardware Policy Interceptor",
            description = "Nanosecond-latency zero-trust hardware filter enforcing real-time domain crossing permissions.",
            normalizedX = 0.80f,
            normalizedY = 0.38f,
            securityProtocol = "ARM TrustZone Sandboxing",
            latencyNs = 45,
            activeLoad = 0.58f,
            isHardwareEnclave = false,
            activePackets = 34
        ),
        CyberNode(
            id = "NODE_ORACLE_SWARM",
            name = "Sub-Agent Swarm Node",
            shortLabel = "Oracle Swarm",
            tierNumber = 4,
            tierLabel = "Multi-Agent Orchestrator",
            description = "Parallel sub-agent orchestrator managing Agent-Alpha, Agent-Beta, and security sentinel tasks.",
            normalizedX = 0.50f,
            normalizedY = 0.55f,
            securityProtocol = "Photonic Quarantined Memory",
            latencyNs = 310,
            activeLoad = 0.72f,
            isHardwareEnclave = false,
            activePackets = 46
        ),
        CyberNode(
            id = "NODE_ENCLAVE_VAULT",
            name = "512-bit Post-Quantum Enclave",
            shortLabel = "PQ Enclave",
            tierNumber = 5,
            tierLabel = "Post-Quantum Memory Vault",
            description = "Isolated eUICC hardware enclave executing Kyber-1024 / Dilithium-5 lattice cryptography.",
            normalizedX = 0.20f,
            normalizedY = 0.78f,
            securityProtocol = "NIST FIPS-203 Kyber-1024",
            latencyNs = 620,
            activeLoad = 0.34f,
            isHardwareEnclave = true,
            activePackets = 18
        ),
        CyberNode(
            id = "NODE_TELEMETRY_SANITIZER",
            name = "Telemetry Sanitizer Node",
            shortLabel = "Sanitizer",
            tierNumber = 6,
            tierLabel = "Differential Privacy Core",
            description = "Automated PII scrubbing engine enforcing mathematical differential privacy bounds (epsilon = 0.5).",
            normalizedX = 0.50f,
            normalizedY = 0.88f,
            securityProtocol = "Laplace Noise Injection Engine",
            latencyNs = 95,
            activeLoad = 0.51f,
            isHardwareEnclave = false,
            activePackets = 22
        ),
        CyberNode(
            id = "NODE_BOUNDARY_GATEWAY",
            name = "Encrypted Boundary Gateway",
            shortLabel = "Boundary Gateway",
            tierNumber = 7,
            tierLabel = "Perimeter Leak Proof Egress",
            description = "Zero-leak outbound network boundary managing encrypted TLS 1.3 tunnels to remote AI clusters.",
            normalizedX = 0.80f,
            normalizedY = 0.78f,
            securityProtocol = "Continuous Perimeter Mathematical Proof",
            latencyNs = 440,
            activeLoad = 0.48f,
            isHardwareEnclave = false,
            activePackets = 16
        )
    )

    val STANDARD_NEURAL_ROUTES = listOf(
        CyberNodeRoute(
            id = "ROUTE_ZERO_TRUST_ATTEST",
            name = "Enclave Attestation & Verification",
            intentType = "ZERO_TRUST_ATTESTATION",
            description = "Biometric intent routed to State Core, verified by Policy Gate, and executed in 512-bit PQ Enclave.",
            nodeHops = listOf("NODE_COMPOSERY", "NODE_VIEWMODEL", "NODE_POLICY_GATE", "NODE_ENCLAVE_VAULT"),
            riskLevel = IntentRiskLevel.SAFE,
            latencyMs = 4,
            cryptographicDigest = "0x8F92A1B7"
        ),
        CyberNodeRoute(
            id = "ROUTE_SUB_AGENT_DISPATCH",
            name = "Sub-Agent Swarm Orchestration",
            intentType = "SUB_AGENT_DISPATCH",
            description = "Multi-modal prompt intercepted by Zero-Trust Gate and distributed to Oracle Swarm for parallel execution.",
            nodeHops = listOf("NODE_COMPOSERY", "NODE_POLICY_GATE", "NODE_ORACLE_SWARM"),
            riskLevel = IntentRiskLevel.SAFE,
            latencyMs = 3,
            cryptographicDigest = "0x3C4D8E9F"
        ),
        CyberNodeRoute(
            id = "ROUTE_CROSS_DOMAIN_MUTATION",
            name = "Cross-Domain Sovereign Gate",
            intentType = "CROSS_DOMAIN_MUTATION",
            description = "Restricted domain crossing requiring biometric gating, enclave encryption, and boundary verification.",
            nodeHops = listOf("NODE_COMPOSERY", "NODE_POLICY_GATE", "NODE_ENCLAVE_VAULT", "NODE_BOUNDARY_GATEWAY"),
            riskLevel = IntentRiskLevel.RESTRICTED,
            latencyMs = 8,
            cryptographicDigest = "0x9E7F1A2D"
        ),
        CyberNodeRoute(
            id = "ROUTE_PERIMETER_LEAK_PROOF",
            name = "Autonomous Perimeter Leak Proof",
            intentType = "PERIMETER_LEAK_PROOF",
            description = "Sub-agent telemetry scrubbed through Differential Privacy Sanitizer before encrypted egress dispatch.",
            nodeHops = listOf("NODE_ORACLE_SWARM", "NODE_TELEMETRY_SANITIZER", "NODE_BOUNDARY_GATEWAY"),
            riskLevel = IntentRiskLevel.SAFE,
            latencyMs = 5,
            cryptographicDigest = "0x5A1B7C3E"
        ),
        CyberNodeRoute(
            id = "ROUTE_TELEMETRY_PII_PURGE",
            name = "Differential Privacy Egress Purge",
            intentType = "TELEMETRY_PII_PURGE",
            description = "Egress data flow sanitized through Laplace noise before committing to immutable persistent vault.",
            nodeHops = listOf("NODE_COMPOSERY", "NODE_VIEWMODEL", "NODE_TELEMETRY_SANITIZER", "NODE_ENCLAVE_VAULT"),
            riskLevel = IntentRiskLevel.SAFE,
            latencyMs = 6,
            cryptographicDigest = "0x2D4F8B0A"
        ),
        CyberNodeRoute(
            id = "ROUTE_ENCLAVE_READ",
            name = "Gated Kyber Enclave Read",
            intentType = "ENCLAVE_READ",
            description = "Protected state query passing policy hardware check to read isolated post-quantum memory.",
            nodeHops = listOf("NODE_COMPOSERY", "NODE_VIEWMODEL", "NODE_POLICY_GATE", "NODE_ENCLAVE_VAULT"),
            riskLevel = IntentRiskLevel.ELEVATED,
            latencyMs = 4,
            cryptographicDigest = "0x6E9C1D4A"
        )
    )

    val DEFAULT_SECURITY_POLICY_RULES = listOf(
        SecurityPolicyRule(
            id = "POL_CROSS_DOMAIN_GATE",
            name = "Explicit Neural Gate Confirmation",
            category = "NEURAL_GATE",
            description = "Mandates explicit operator biometric confirmation before crossing sensitive micro-domain boundaries.",
            isEnabled = true,
            minimumTier = 0,
            requiresBiometricConfirmation = true,
            enforcementAction = "Hold execution in biometric gate queue; issue haptic alert."
        ),
        SecurityPolicyRule(
            id = "POL_ENCLAVE_KYBER_512",
            name = "512-bit Post-Quantum Enclave Sealing",
            category = "ENCLAVE_CRYPTO",
            description = "Enforces Kyber-1024 / Dilithium-5 lattice encryption keys stored strictly in isolated hardware enclave memory.",
            isEnabled = true,
            minimumTier = 0,
            requiresBiometricConfirmation = true,
            enforcementAction = "Reject non-enclave key material; enforce 60s dynamic rotation."
        ),
        SecurityPolicyRule(
            id = "POL_DIFF_PRIVACY_EPSILON",
            name = "Differential Privacy Telemetry Sanitization",
            category = "TELEMETRY_PRIVACY",
            description = "Applies Laplace / Gaussian noise injection (ε = 0.5) and strips sensitive tokens prior to external network egress.",
            isEnabled = true,
            minimumTier = 1,
            requiresBiometricConfirmation = false,
            enforcementAction = "Mask IP, raw biometrics, and bearer tokens with cryptographic SHA-256 hashes."
        ),
        SecurityPolicyRule(
            id = "POL_PROMPT_INJECTION_SHIELD",
            name = "Dynamic Shield Heuristic Inspection",
            category = "SHIELD_DEFENSE",
            description = "Analyzes all intent vectors against prompt-injection, buffer overflows, and memory taint payloads.",
            isEnabled = true,
            minimumTier = 1,
            requiresBiometricConfirmation = false,
            enforcementAction = "Instantaneous Photonic Crimson isolation; write incident to encrypted audit ledger."
        ),
        SecurityPolicyRule(
            id = "POL_DETERMINISTIC_PROOF",
            name = "Pre-Execution Mathematical Invariant Proofs",
            category = "AUTONOMOUS_VALIDATION",
            description = "Requires automated verification of bit-for-bit build reproducibility and zero-loss schema integrity before dispatch.",
            isEnabled = true,
            minimumTier = 2,
            requiresBiometricConfirmation = false,
            enforcementAction = "Block autonomous sub-agent orchestration if verification proof digest mismatches."
        )
    )

    fun generateAgis2045Json(): String {
        return """
{
  "system_version": "AGIS-2045",
  "codename": "Quantum Glass",
  "architecture_type": "Zero-Trust Biomorphic Cyber-Node",
  "layers": [
    {
      "layer_id": 1,
      "name": "Quantum Glass Visual System",
      "timestamp": "0:55",
      "color_palette": {
        "base": "Deep Space Cobalt",
        "translucent_structure": "Volumetric Glass Panels",
        "signals": {
          "activity": "Photonic Cyan",
          "primary_content": "Ambient White",
          "healthy_status": "Operational Emerald",
          "threat_containment": "Crimson"
        }
      },
      "core_rule": "Spatial depth clarifies neural actions without obscuring the underlying security posture."
    },
    {
      "layer_id": 2,
      "name": "Neural Interaction & Intent Routing",
      "timestamp": "1:40",
      "components": {
        "header": "Real-Time Biometrics & Active Sub-Agent Threads",
        "domain_routing": "Explicit Neural Confirmation Required",
        "status_signals": "Continuous Enclave Isolation Indicators",
        "input_surface": "Glass Input Pane (Multi-Modal & Direct Intent)"
      }
    },
    {
      "layer_id": 3,
      "name": "Hardened System Architecture",
      "timestamp": "2:25",
      "data_flow": [
        {
          "tier": "Visual Layer",
          "component": "Neural Composery",
          "function": "Renders responsive interface components based on verified intent."
        },
        {
          "tier": "State Management",
          "component": "Active ViewModel",
          "function": "Maintains unidirectional, immutable state representations."
        },
        {
          "tier": "Control Gates",
          "component": "Zero-Trust Hardware Filters",
          "function": "Enforces real-time policy checks before processing operations."
        },
        {
          "tier": "Storage Vault",
          "component": "Post-Quantum Encrypted Database",
          "function": "Writes to local persistent memory via hardware-backed ciphers."
        },
        {
          "tier": "Boundary",
          "component": "Encrypted Network Interface",
          "function": "Manages outbound traffic to remote AGI model clusters."
        }
      ]
    },
    {
      "layer_id": 4,
      "name": "Shield Protection Path",
      "timestamp": "3:25",
      "pipeline_steps": [
        "Continuous Authentication",
        "Threat Scanning",
        "Data Sanitization",
        "Provenance Tracing",
        "Audit Logging"
      ]
    },
    {
      "layer_id": 5,
      "name": "Post-Quantum Local Enclave",
      "timestamp": "4:30",
      "security_specifications": {
        "key_type": "512-bit Post-Quantum Dynamic Keys",
        "hardware_backing": "Secure Enclave Hardware",
        "isolation_model": "Dual Isolation (Hardware Memory Encryption + OS Memory Sandboxing)"
      }
    },
    {
      "layer_id": 6,
      "name": "Continuous Autonomous Validation",
      "timestamp": "5:30",
      "pre_execution_checks": [
        "Deterministic Builds",
        "Migration Verification",
        "Perimeter Proofs"
      ]
    }
  ],
  "tag_optimizations_2026": [
    "AI Agent Security",
    "DevSecOps AI",
    "Zero Trust Architecture",
    "LLM Application Security",
    "AI Threat Defense"
  ]
}
        """.trimIndent()
    }
}
