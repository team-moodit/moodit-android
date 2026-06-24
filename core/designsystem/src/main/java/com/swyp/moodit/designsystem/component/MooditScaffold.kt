package com.swyp.moodit.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        contentWindowInsets = WindowInsets(0.dp,0.dp,0.dp,0.dp),
        topBar = topBar,
        bottomBar = {
            Box(modifier = Modifier.navigationBarsPadding()) {
                bottomBar()
            }
        },
        snackbarHost = snackbarHost
    ) { paddingValues ->
        content(paddingValues)
    }
}