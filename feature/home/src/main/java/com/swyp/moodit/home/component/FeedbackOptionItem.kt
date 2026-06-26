package com.swyp.moodit.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.FeedbackOption

@Composable
fun FeedbackOptionItem(
    feedbackOption: FeedbackOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderModifier = if (isSelected) {
        Modifier
            .border(
                1.dp,
                MooditTheme.colors.primary,
                RoundedCornerShape(8.dp)
            )
            .background(
                color = MooditTheme.colors.primary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
    } else {
        Modifier
            .border(
                1.dp,
                MooditTheme.colors.onSurfaceContainer,
                RoundedCornerShape(8.dp)
            )
            .background(
                color = MooditTheme.colors.onPrimary,
                shape = RoundedCornerShape(8.dp)
            )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(borderModifier)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = feedbackOption.content,
            color = if (isSelected) MooditTheme.colors.primary else MooditTheme.colors.tertiary,
            style = MooditTheme.typography.b2Medium
        )
    }
}

@Preview
@Composable
fun FeedbackOptionPreview() {
    MooditTheme {
        val testFeedbackOption = FeedbackOption(1L, "이 스타일이 저랑 안 맞았어요")
        FeedbackOptionItem(
            feedbackOption = testFeedbackOption,
            isSelected = true,
            onClick = { }
        )
    }
}