package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Immutable
data class PhotonicSignals(
    val emeraldOperational: Color = OperationalEmerald,
    val emeraldGlow: Color = OperationalEmeraldGlow,
    val cyanPhotonic: Color = PhotonicCyan,
    val cyanGlow: Color = PhotonicCyanGlow,
    val crimsonContainment: Color = ContainmentCrimson,
    val crimsonGlow: Color = ContainmentCrimsonGlow,
    val spaceCobaltDark: Color = SpaceCobaltDark,
    val spaceCobaltSurface: Color = SpaceCobaltSurface,
    val spaceCobaltGlass: Color = SpaceCobaltGlass,
    val spaceCobaltGlassBorder: Color = SpaceCobaltGlassBorder
)

val LocalPhotonicSignals = staticCompositionLocalOf { PhotonicSignals() }

val MaterialTheme.photonicSignals: PhotonicSignals
    @Composable
    @ReadOnlyComposable
    get() = LocalPhotonicSignals.current

private val AgisQuantumColorScheme = darkColorScheme(
    primary = PhotonicCyan,
    onPrimary = SpaceCobaltDark,
    primaryContainer = SpaceCobaltGlassElevated,
    onPrimaryContainer = PhotonicCyanLight,
    secondary = OperationalEmerald,
    onSecondary = SpaceCobaltDark,
    secondaryContainer = OperationalEmeraldGlow,
    onSecondaryContainer = OperationalEmerald,
    tertiary = QuantumViolet,
    onTertiary = SpaceCobaltDark,
    tertiaryContainer = QuantumVioletGlow,
    onTertiaryContainer = QuantumVioletLight,
    error = ContainmentCrimson,
    onError = AmbientWhite,
    errorContainer = ContainmentCrimsonGlow,
    onErrorContainer = ContainmentCrimson,
    background = SpaceCobaltDark,
    onBackground = AmbientWhite,
    surface = SpaceCobaltSurface,
    onSurface = AmbientWhite,
    surfaceVariant = SpaceCobaltCard,
    onSurfaceVariant = AmbientWhiteMuted,
    outline = SpaceCobaltGlassBorder
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = SpaceCobaltDark.toArgb()
            window.navigationBarColor = SpaceCobaltDark.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }

    CompositionLocalProvider(
        LocalPhotonicSignals provides PhotonicSignals()
    ) {
        MaterialTheme(
            colorScheme = AgisQuantumColorScheme,
            typography = Typography,
            content = content
        )
    }
}

/**
 * Explicit Immersive UI Theme alias for direct semantic referencing.
 */
@Composable
fun ImmersiveUiTheme(
    content: @Composable () -> Unit
) {
    MyApplicationTheme(content = content)
}

