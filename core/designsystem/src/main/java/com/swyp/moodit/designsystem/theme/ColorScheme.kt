package com.swyp.moodit.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceContainer: Color,
    val onSurfaceContainer: Color,
    val error: Color,
    val borderDefault: Color,
    val textSecondary: Color,
    val scrim: Color
)

val lightColorScheme = CustomColorScheme(
    primary = Green500,
    onPrimary = Gray1000,
    primaryContainer = Gray1000,
    onPrimaryContainer = Gray100,
    secondary = Green500.copy(alpha = 0.1f),
    onSecondary = Green500,
    tertiary = Gray200,
    onTertiary = Gray300,
    background = Gray1100,
    onBackground = Gray100,
    surface = Gray500,
    onSurface = Gray700,
    surfaceContainer = Gray900,
    onSurfaceContainer = Gray800,
    error = error,
    borderDefault = Gray600,
    textSecondary = Gray400,
    scrim = Color.Black.copy(alpha = 0.5f)
)

val darkColorScheme = CustomColorScheme(
    primary = Green400,
    onPrimary = Gray1100,
    primaryContainer = Green900,
    onPrimaryContainer = Green10,
    secondary = Green400.copy(alpha = 0.15f),
    onSecondary = Green400,
    tertiary = Gray800,
    onTertiary = Gray600,
    background = Gray1100,
    onBackground = Gray100,
    surface = Gray900,
    onSurface = Gray200,
    surfaceContainer = Gray1000,
    onSurfaceContainer = Gray300,
    error = error,
    borderDefault = Gray800,
    textSecondary = Gray400,
    scrim = Color.Black.copy(alpha = 0.6f)
)
