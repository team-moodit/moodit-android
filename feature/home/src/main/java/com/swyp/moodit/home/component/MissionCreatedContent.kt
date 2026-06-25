package com.swyp.moodit.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.MissionTag
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun MissionCreatedContent(tagContent: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MissionTag(content = tagContent)
        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
            text = "후보와 비슷한 색감으로\n하루 코디해보기",
            style = MooditTheme.typography.h2,
            color = MooditTheme.colors.onPrimaryContainer,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MissionInfoCard(
                modifier = Modifier.weight(1f), title = "진행된 라운드 수", content = "16강"
            )
            MissionInfoCard(
                modifier = Modifier.weight(1f), title = "무드매치 완료 날짜", content = "26.06.10"
            )
        }
    }
}

@Preview
@Composable
fun MissionCreatedContentPreview() {
    MooditTheme {
        MissionCreatedContent("MISSION")
    }
}