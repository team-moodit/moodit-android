package com.swyp.moodit.designsystem

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun MissionTag(
    modifier: Modifier = Modifier,
    content: String
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .border(
                width = 1.dp,
                color = MooditTheme.colors.primary,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = content,
            color = MooditTheme.colors.primary,
            style = MooditTheme.typography.caption
        )
    }
}

@Preview
@Composable
fun MissionTagPreview() {
    MooditTheme {
        MissionTag(content = "MISSION")
    }
}