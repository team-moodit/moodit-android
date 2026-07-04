package com.swyp.moodit.report.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun SatisfactionLinearProgressBar(
    modifier: Modifier = Modifier,
    rate: Double = 3.0
) {
    val progressRatio = (rate / 5.0).toFloat().coerceIn(0f, 1f)

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(color = MooditTheme.colors.onSurfaceContainer)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progressRatio)
                    .fillMaxHeight()
                    .clip(
                        shape = RoundedCornerShape(999.dp)
                    )
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFDCFAA4),
                                MooditTheme.colors.primary
                            )
                        )
                    )
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "안 맞았다",
                style = MooditTheme.typography.b2ExtraSmall,
                color = MooditTheme.colors.tertiary
            )
            Text(
                text = "보통",
                style = MooditTheme.typography.b2ExtraSmall,
                color = MooditTheme.colors.tertiary
            )
            Text(
                text = "잘 맞았다",
                style = MooditTheme.typography.b2ExtraSmall,
                color = MooditTheme.colors.tertiary
            )
        }
    }
}

@Preview
@Composable
fun SatisfactionLinearProgressBarPreview() {
    MooditTheme {
        SatisfactionLinearProgressBar(modifier = Modifier)
    }
}