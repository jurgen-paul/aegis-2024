package com.example.ui.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Volumetric Animation Specifications for Quantum Glass UI
 */
object QuantumAnimationSpecs {
    val FluidSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )

    val SnappySpring = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )

    val SubtleDrift = infiniteRepeatable<Float>(
        animation = tween(durationMillis = 4000, easing = LinearEasing),
        repeatMode = RepeatMode.Restart
    )

    val PhotonicPulse = infiniteRepeatable<Float>(
        animation = tween(durationMillis = 2400, easing = FastOutSlowInEasing),
        repeatMode = RepeatMode.Reverse
    )

    val RefractionSweepSpec = infiniteRepeatable<Float>(
        animation = tween(durationMillis = 3200, easing = LinearEasing),
        repeatMode = RepeatMode.Restart
    )
}

/**
 * Interactive volumetric state that records pointer tilt, hover state, and photonic illumination intensity.
 */
@Stable
class VolumetricInteractiveState(
    val interactionSource: MutableInteractionSource
) {
    var rawPointerOffset by mutableStateOf(Offset.Zero)
    var isInteracting by mutableStateOf(false)
}

@Composable
fun rememberVolumetricInteractiveState(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
): VolumetricInteractiveState {
    return remember(interactionSource) {
        VolumetricInteractiveState(interactionSource)
    }
}

/**
 * Modifier that transforms any Composable into a 3D volumetric quantum glass element.
 * Applies:
 * - Dynamic 3D perspective rotation (tiltX & tiltY) on hover or touch drag
 * - Scale magnification (breathing on hover/press)
 * - Dynamic multi-stop photonic gradient border (photonic cyan to emerald & deep space cobalt)
 * - Refractive specular highlight sheen sweeping across the glass surface
 * - Luminous photonic drop-shadow with emerald & cyan ambient dispersion
 */
fun Modifier.volumetricQuantumGlass(
    state: VolumetricInteractiveState? = null,
    maxTiltAngle: Float = 12f,
    elevation: Dp = 6.dp,
    shapeRadius: Dp = 16.dp,
    primarySignalColor: Color = PhotonicCyan,
    secondarySignalColor: Color = OperationalEmerald,
    glassBackground: Color = SpaceCobaltGlass,
    enableSpecularSweep: Boolean = true,
    enablePointerTilt: Boolean = true,
    scaleOnHover: Float = 1.025f
): Modifier = composed {
    val internalInteractionSource = state?.interactionSource ?: remember { MutableInteractionSource() }
    val isHovered by internalInteractionSource.collectIsHoveredAsState()
    val isPressed by internalInteractionSource.collectIsPressedAsState()

    var touchOffset by remember { mutableStateOf(Offset.Zero) }
    var containerSize by remember { mutableStateOf(Size.Zero) }

    // Continuous photonic phase for border & shimmer rotation
    val infiniteTransition = rememberInfiniteTransition(label = "QuantumVolumetricInfinite")
    val ambientPhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = QuantumAnimationSpecs.SubtleDrift,
        label = "AmbientPhase"
    )

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = QuantumAnimationSpecs.PhotonicPulse,
        label = "PhotonicPulse"
    )

    // Calculate target tilt angles from touch/hover
    val targetRotationX = when {
        isPressed || (touchOffset != Offset.Zero && isHovered) -> {
            if (containerSize.height > 0) {
                val normalizedY = ((touchOffset.y / containerSize.height) - 0.5f) * 2f
                -normalizedY * maxTiltAngle
            } else 0f
        }
        isHovered -> -maxTiltAngle * 0.4f
        else -> 0f
    }

    val targetRotationY = when {
        isPressed || (touchOffset != Offset.Zero && isHovered) -> {
            if (containerSize.width > 0) {
                val normalizedX = ((touchOffset.x / containerSize.width) - 0.5f) * 2f
                normalizedX * maxTiltAngle
            } else 0f
        }
        isHovered -> maxTiltAngle * 0.4f
        else -> 0f
    }

    val targetScale = when {
        isPressed -> 0.985f
        isHovered -> scaleOnHover
        else -> 1.0f
    }

    val animatedRotationX by animateFloatAsState(
        targetValue = targetRotationX,
        animationSpec = QuantumAnimationSpecs.FluidSpring,
        label = "VolumetricRotationX"
    )

    val animatedRotationY by animateFloatAsState(
        targetValue = targetRotationY,
        animationSpec = QuantumAnimationSpecs.FluidSpring,
        label = "VolumetricRotationY"
    )

    val animatedScale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = QuantumAnimationSpecs.FluidSpring,
        label = "VolumetricScale"
    )

    val animatedGlowAlpha by animateFloatAsState(
        targetValue = if (isHovered || isPressed) 0.65f else 0.25f,
        animationSpec = tween(300),
        label = "GlowAlpha"
    )

    val density = LocalDensity.current

    this
        .hoverable(interactionSource = internalInteractionSource)
        .then(
            if (enablePointerTilt) {
                Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { offset ->
                            touchOffset = offset
                            state?.rawPointerOffset = offset
                            state?.isInteracting = true
                            tryAwaitRelease()
                            touchOffset = Offset.Zero
                            state?.isInteracting = false
                        }
                    )
                }
            } else Modifier
        )
        .graphicsLayer {
            rotationX = animatedRotationX
            rotationY = animatedRotationY
            scaleX = animatedScale
            scaleY = animatedScale
            cameraDistance = 14f * density.density
            shadowElevation = (elevation.value * (if (isHovered) 1.5f else 1.0f))
            shape = androidx.compose.foundation.shape.RoundedCornerShape(shapeRadius)
            clip = false
        }
        // Draw volumetric photonic shadow & ambient glow behind
        .drawBehind {
            containerSize = size
            val cornerRadiusPx = shapeRadius.toPx()

            // Dynamic light source reflection based on tilt
            val lightCenter = Offset(
                x = size.width * (0.5f + (animatedRotationY / maxTiltAngle) * 0.35f),
                y = size.height * (0.5f - (animatedRotationX / maxTiltAngle) * 0.35f)
            )

            // Deep Space Cobalt and Photonic Emerald/Cyan Ambient Glow
            drawRoundRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        primarySignalColor.copy(alpha = animatedGlowAlpha * 0.45f * pulseScale),
                        secondarySignalColor.copy(alpha = animatedGlowAlpha * 0.25f),
                        SpaceCobaltDark.copy(alpha = 0.8f),
                        Color.Transparent
                    ),
                    center = lightCenter,
                    radius = (size.maxDimension * 0.8f).coerceAtLeast(10f)
                ),
                size = size,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadiusPx)
            )
        }
        // Draw refractive specular sheen over the content
        .drawWithContent {
            drawContent()

            val cornerRadiusPx = shapeRadius.toPx()

            // Photonic Perimeter Border (Rotating Phase)
            val borderAngle = ambientPhase * 2 * PI.toFloat()
            val startOffset = Offset(
                x = size.width * (0.5f + 0.5f * cos(borderAngle)),
                y = size.height * (0.5f + 0.5f * sin(borderAngle))
            )
            val endOffset = Offset(
                x = size.width * (0.5f - 0.5f * cos(borderAngle)),
                y = size.height * (0.5f - 0.5f * sin(borderAngle))
            )

            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        primarySignalColor.copy(alpha = if (isHovered) 0.95f else 0.60f),
                        secondarySignalColor.copy(alpha = if (isHovered) 0.70f else 0.30f),
                        SpaceCobaltGlassBorder.copy(alpha = 0.20f),
                        primarySignalColor.copy(alpha = if (isHovered) 0.80f else 0.40f)
                    ),
                    start = startOffset,
                    end = endOffset
                ),
                size = size,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadiusPx),
                style = Stroke(width = if (isHovered) 1.8.dp.toPx() else 1.2.dp.toPx())
            )

            // Volumetric Specular Refraction Sweep
            if (enableSpecularSweep) {
                val sweepX = ((ambientPhase * 2f) - 0.5f) * size.width
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            primarySignalColor.copy(alpha = if (isHovered) 0.18f else 0.07f),
                            secondarySignalColor.copy(alpha = if (isHovered) 0.12f else 0.04f),
                            Color.Transparent
                        ),
                        start = Offset(sweepX - 80f, 0f),
                        end = Offset(sweepX + 80f, size.height)
                    ),
                    size = size,
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadiusPx)
                )
            }
        }
}

/**
 * Modifier to create a rhythmic photonic glow pulse around a status indicator or container.
 */
fun Modifier.photonicGlowPulse(
    signalColor: Color = OperationalEmerald,
    baseAlpha: Float = 0.2f,
    targetAlpha: Float = 0.75f,
    pulseDurationMs: Int = 1800,
    radiusMultiplier: Float = 1.6f
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "PhotonicGlowPulse")
    val animatedAlpha by infiniteTransition.animateFloat(
        initialValue = baseAlpha,
        targetValue = targetAlpha,
        animationSpec = infiniteRepeatable(
            animation = tween(pulseDurationMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowAlpha"
    )

    val animatedRadiusScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = radiusMultiplier,
        animationSpec = infiniteRepeatable(
            animation = tween(pulseDurationMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "RadiusScale"
    )

    this.drawBehind {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    signalColor.copy(alpha = animatedAlpha),
                    signalColor.copy(alpha = animatedAlpha * 0.35f),
                    Color.Transparent
                ),
                center = center,
                radius = (size.minDimension / 2f) * animatedRadiusScale
            )
        )
    }
}

/**
 * Modifier for fluid laser/photonic refraction sweep along a component's surface.
 */
fun Modifier.quantumRefractionSweep(
    primaryColor: Color = PhotonicCyan,
    secondaryColor: Color = OperationalEmerald,
    durationMs: Int = 3000
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "QuantumRefraction")
    val phase by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMs, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "SweepPhase"
    )

    this.drawWithContent {
        drawContent()
        val startX = phase * size.width
        val sweepWidth = size.width * 0.45f

        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.Transparent,
                    primaryColor.copy(alpha = 0.15f),
                    secondaryColor.copy(alpha = 0.10f),
                    Color.Transparent
                ),
                start = Offset(startX, 0f),
                end = Offset(startX + sweepWidth, size.height)
            )
        )
    }
}

/**
 * Modifier providing layered volumetric parallax displacement for interior elements.
 */
fun Modifier.volumetricParallaxContent(
    depthFactor: Float = 1.0f,
    state: VolumetricInteractiveState
): Modifier = composed {
    val animatedOffsetX by animateFloatAsState(
        targetValue = if (state.isInteracting) state.rawPointerOffset.x * 0.05f * depthFactor else 0f,
        animationSpec = QuantumAnimationSpecs.FluidSpring,
        label = "ParallaxX"
    )
    val animatedOffsetY by animateFloatAsState(
        targetValue = if (state.isInteracting) state.rawPointerOffset.y * 0.05f * depthFactor else 0f,
        animationSpec = QuantumAnimationSpecs.FluidSpring,
        label = "ParallaxY"
    )

    this.graphicsLayer {
        translationX = animatedOffsetX
        translationY = animatedOffsetY
    }
}
