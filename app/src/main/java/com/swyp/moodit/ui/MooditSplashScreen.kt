package com.swyp.moodit.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme
import kotlinx.coroutines.delay

@Composable
fun MooditSplashScreen(
    onSplashFinished: () -> Unit = {}
) {
    val scale = remember { Animatable(0.6f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800)
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600)
        )
        delay(1200)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MooditTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(com.swyp.moodit.designsystem.R.drawable.moodit_logo_title),
            modifier = Modifier
                .width(150.dp)
                .graphicsLayer(
                    scaleX = scale.value,
                    scaleY = scale.value,
                    alpha = alpha.value
                ),
            contentDescription = "moodit_logo_title"
        )
    }
}