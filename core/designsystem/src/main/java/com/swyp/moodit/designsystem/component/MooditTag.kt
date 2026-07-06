package com.swyp.moodit.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun MooditTag(
    modifier: Modifier = Modifier
        .wrapContentSize()
        .border(
            width = 1.dp,
            color = MooditTheme.colors.primary,
            shape = RoundedCornerShape(100.dp)
        )
        .padding(horizontal = 12.dp, vertical = 10.dp),
    textStyle: TextStyle = MooditTheme.typography.caption,
    textColor: Color = MooditTheme.colors.primary,
    content: String
) {
    Surface(
        modifier = modifier,
        color = Color.Transparent
    ) {
        Text(
            text = content,
            color = textColor,
            style = textStyle
        )
    }
}

@Preview
@Composable
fun MissionTagPreview() {
    MooditTheme {
        MooditTag(content = "MISSION")
    }
}