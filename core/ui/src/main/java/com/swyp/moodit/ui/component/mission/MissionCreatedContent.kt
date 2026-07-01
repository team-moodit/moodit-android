package com.swyp.moodit.ui.component.mission

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
import com.swyp.moodit.common.util.DateUtil.toFormatDate
import com.swyp.moodit.designsystem.MissionTag
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.Mission

@Composable
fun MissionCreatedContent(tagContent: String, mission: Mission) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MissionTag(content = tagContent)
        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
            text = mission.missionTitle,
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
                modifier = Modifier.weight(1f),
                title = "진행된 라운드 수",
                content = "${mission.matchResult.matchRoundCount}회"
            )
            MissionInfoCard(
                modifier = Modifier.weight(1f),
                title = "무드매치 완료 날짜",
                content = mission.matchResult.matchCompletedAt.toFormatDate()
            )
        }
    }
}

@Preview
@Composable
fun MissionCreatedContentPreview() {
    MooditTheme {
        MissionCreatedContent(tagContent = "MISSION", mission = Mission())
    }
}