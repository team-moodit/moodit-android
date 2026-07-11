package com.swyp.moodit.onboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun OnboardingIndicator(selectedIndex: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .width(if (isSelected) 20.dp else 8.dp)
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Color(0xFFDCFAA4) else MooditTheme.colors.onSurface)
            )
        }
    }
}

@Preview
@Composable
fun OnboardingIndicatorPreview() {
    MooditTheme {
        OnboardingIndicator(selectedIndex = 0)
    }
}