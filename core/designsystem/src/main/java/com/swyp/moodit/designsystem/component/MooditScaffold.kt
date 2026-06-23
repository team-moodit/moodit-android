package com.swyp.moodit.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun MooditScaffold(
    modifier: Modifier = Modifier,
    containerColor: Color = MooditTheme.colors.background,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = containerColor,
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost
    ) { paddingValues ->
        content(paddingValues)
    }
}