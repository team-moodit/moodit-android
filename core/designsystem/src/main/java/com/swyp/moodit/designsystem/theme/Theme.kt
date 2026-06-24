package com.swyp.moodit.designsystem.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

internal val LocalMooditColorScheme = staticCompositionLocalOf { lightColorScheme }
internal val LocalMooditTypography = staticCompositionLocalOf { AppTypography }

object MooditTheme {
    val colors: CustomColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalMooditColorScheme.current

    val typography: MooditTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalMooditTypography.current
}

@Composable
fun MooditTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) darkColorScheme else lightColorScheme

    CompositionLocalProvider(
        LocalMooditColorScheme provides colors,
        LocalMooditTypography provides AppTypography,
        content = content,
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        val insetsController = WindowCompat.getInsetsController(window, view)
        // 앱 배경이 항상 어두우므로 상태바/네비바 아이콘은 항상 밝게(false) 설정
        insetsController.isAppearanceLightNavigationBars = false
        insetsController.isAppearanceLightStatusBars = false
    }
}
