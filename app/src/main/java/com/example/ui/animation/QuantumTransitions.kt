package com.example.ui.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

/**
 * High-level Volumetric Quantum Glass Card with real-time 3D tilt, dynamic hover effects,
 * and fluid photonic luminescence.
 */
@Composable
fun QuantumInteractiveCard(
    modifier: Modifier = Modifier,
    primarySignalColor: Color = PhotonicCyan,
    secondarySignalColor: Color = OperationalEmerald,
    backgroundColor: Color = SpaceCobaltGlass,
    shapeRadius: Dp = 16.dp,
    elevation: Dp = 6.dp,
    maxTiltAngle: Float = 10f,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.(VolumetricInteractiveState) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val volumetricState = rememberVolumetricInteractiveState(interactionSource)
    val cardShape = RoundedCornerShape(shapeRadius)

    val clickableModifier = if (onClick != null) {
        Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
    } else Modifier

    Box(
        modifier = modifier
            .volumetricQuantumGlass(
                state = volumetricState,
                maxTiltAngle = maxTiltAngle,
                elevation = elevation,
                shapeRadius = shapeRadius,
                primarySignalColor = primarySignalColor,
                secondarySignalColor = secondarySignalColor,
                glassBackground = backgroundColor,
                enableSpecularSweep = true,
                enablePointerTilt = true
            )
            .clip(cardShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundColor.copy(alpha = 0.96f),
                        backgroundColor.copy(alpha = 0.72f),
                        SpaceCobaltSurface.copy(alpha = 0.88f)
                    )
                )
            )
            .then(clickableModifier)
            .padding(16.dp)
    ) {
        Column {
            content(volumetricState)
        }
    }
}

/**
 * Volumetric fluid transition container that smoothly crossfades content with
 * scale-depth morphing and photonic luminescence flares.
 */
@Composable
fun <T> QuantumFluidCrossfade(
    targetState: T,
    modifier: Modifier = Modifier,
    animationDurationMs: Int = 400,
    content: @Composable (T) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        modifier = modifier,
        transitionSpec = {
            (fadeIn(animationSpec = tween(animationDurationMs, easing = FastOutSlowInEasing)) +
                    scaleIn(
                        initialScale = 0.94f,
                        animationSpec = tween(animationDurationMs, easing = FastOutSlowInEasing)
                    ))
                .togetherWith(
                    fadeOut(animationSpec = tween(animationDurationMs / 2, easing = LinearEasing)) +
                            scaleOut(
                                targetScale = 1.04f,
                                animationSpec = tween(animationDurationMs / 2, easing = LinearEasing)
                            )
                )
        },
        label = "QuantumFluidCrossfade"
    ) { state ->
        content(state)
    }
}

/**
 * Rhythmic Photonic Signal Pulse Indicator with radiating concentric diffusion rings.
 */
@Composable
fun PhotonicSignalPulseIndicator(
    modifier: Modifier = Modifier,
    signalColor: Color = OperationalEmerald,
    size: Dp = 12.dp,
    pulseSpeedMs: Int = 1800
) {
    val infiniteTransition = rememberInfiniteTransition(label = "PhotonicSignalPulseInfinite")
    val waveScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 2.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(pulseSpeedMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "WaveScale"
    )

    val waveAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(pulseSpeedMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "WaveAlpha"
    )

    Box(
        modifier = modifier.size(size * 2.4f),
        contentAlignment = Alignment.Center
    ) {
        // Outer radiating pulse wave
        Box(
            modifier = Modifier
                .size(size)
                .graphicsLayer {
                    scaleX = waveScale
                    scaleY = waveScale
                    alpha = waveAlpha
                }
                .clip(CircleShape)
                .background(signalColor)
        )

        // Middle soft photonic halo
        Box(
            modifier = Modifier
                .size(size * 1.35f)
                .clip(CircleShape)
                .background(signalColor.copy(alpha = 0.35f))
        )

        // Core bright dot
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            AmbientWhite,
                            signalColor,
                            signalColor.copy(alpha = 0.8f)
                        )
                    )
                )
                .border(1.dp, AmbientWhite.copy(alpha = 0.8f), CircleShape)
        )
    }
}

/**
 * Volumetric interactive Action Button with tactile press depth, energy charge sheen,
 * and fluid photonic hover response.
 */
@Composable
fun QuantumVolumetricButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    primaryColor: Color = PhotonicCyan,
    secondaryColor: Color = OperationalEmerald,
    containerColor: Color = SpaceCobaltGlassElevated,
    shapeRadius: Dp = 12.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val volumetricState = rememberVolumetricInteractiveState(interactionSource)

    val buttonShape = RoundedCornerShape(shapeRadius)

    Box(
        modifier = modifier
            .volumetricQuantumGlass(
                state = volumetricState,
                maxTiltAngle = 8f,
                elevation = if (isPressed) 2.dp else 6.dp,
                shapeRadius = shapeRadius,
                primarySignalColor = primaryColor,
                secondarySignalColor = secondaryColor,
                glassBackground = containerColor,
                scaleOnHover = 1.03f
            )
            .clip(buttonShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        containerColor.copy(alpha = 0.95f),
                        primaryColor.copy(alpha = 0.18f),
                        containerColor.copy(alpha = 0.85f)
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = primaryColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = AmbientWhite,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )
        }
    }
}
