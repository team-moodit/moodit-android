package com.swyp.moodit.ui.component.mission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun MissionInfoCard(
    modifier: Modifier = Modifier,
    title: String,
    content: String
) {
    Surface(
        modifier = modifier.wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        color = MooditTheme.colors.onPrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MooditTheme.typography.b3Medium,
                color = MooditTheme.colors.textSecondary
            )
            Text(
                text = content,
                style = MooditTheme.typography.b2Medium,
                color = MooditTheme.colors.onPrimaryContainer
            )
        }
    }
}

@Preview
@Composable
fun MissionInfoCardPreview() {
    MooditTheme {
        val testTitle = "진행된 라운드 수"
        val testContent = "16강"
        MissionInfoCard(title = testTitle, content = testContent)
    }
}