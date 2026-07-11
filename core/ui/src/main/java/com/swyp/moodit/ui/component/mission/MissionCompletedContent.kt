package com.swyp.moodit.ui.component.mission

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.common.util.DateUtil.toFormatDate
import com.swyp.moodit.common.util.TextUtil
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditTag
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionMatchResult
import com.swyp.moodit.model.MissionState
import com.swyp.moodit.model.PreferenceResultType

@Composable
fun MissionCompletedContent(tagContent: String, mission: Mission) {
    val findPreferenceType = mission.matchResult.preferenceResultType != PreferenceResultType.TIE
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MooditTag(content = tagContent)
        val missionTitle = mission.missionTitle.split("\n")
        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
            text = "${missionTitle.first()}\n${missionTitle.last()}",
            style = MooditTheme.typography.h2,
            color = MooditTheme.colors.onPrimaryContainer,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MooditTheme.colors.onPrimary, shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "미션 완료 날짜",
                color = MooditTheme.colors.textSecondary,
                style = MooditTheme.typography.b3Medium
            )
            Text(
                text = mission.missionCompletedAt.toFormatDate(),
                color = MooditTheme.colors.onPrimaryContainer,
                style = MooditTheme.typography.b2Medium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (mission.missionState == MissionState.REVIEWED) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MooditTheme.colors.onPrimary, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(R.drawable.star_filled),
                    contentDescription = "icon_mission_satisfaction",
                    tint = MooditTheme.colors.primary,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = "${mission.satisfactionScore}점",
                    color = MooditTheme.colors.primary,
                    style = MooditTheme.typography.b3Medium
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MooditTheme.colors.onPrimary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MooditTheme.colors.surfaceContainer)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.bomb),
                            contentDescription = "icon_mood_result",
                            tint = MooditTheme.colors.onTertiary,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "평가 전",
                            style = MooditTheme.typography.b3Medium,
                            color = MooditTheme.colors.tertiary
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "직접 해보니 어땠나요?",
                        color = MooditTheme.colors.onBackground,
                        style = MooditTheme.typography.b2Medium
                    )

                    Text(
                        text = "경험을 남기면 취향의 기준이 뚜렷해져요",
                        color = MooditTheme.colors.textSecondary,
                        style = MooditTheme.typography.b3Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "무드매치 정보",
            style = MooditTheme.typography.b1Medium,
            color = MooditTheme.colors.onPrimaryContainer
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MooditTheme.colors.onPrimary, shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (findPreferenceType) MooditTheme.colors.primary.copy(0.1f) else MooditTheme.colors.surfaceContainer)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(if (findPreferenceType) R.drawable.reward_stars else R.drawable.face_sad_tear),
                    contentDescription = "icon_mood_result",
                    tint = if (findPreferenceType) MooditTheme.colors.primary else MooditTheme.colors.onTertiary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = if (findPreferenceType) "이번 무드매치는\n${mission.matchResult.matchPreferenceTypeTitle}${
                    TextUtil.attachParticle(
                        mission.matchResult.matchPreferenceTypeTitle
                    )
                } 가장 중요하게 생각했어요" else "이번 무드매치는\n뚜렷한 취향의 기준이 없었어요",
                color = MooditTheme.colors.tertiary,
                style = MooditTheme.typography.b3Medium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

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
fun MissionCompletedContentPreview() {
    MooditTheme {
        MissionCompletedContent(
            tagContent = "완료",
            mission = Mission(
                missionTitle = "후보와 비슷한 색감으로\n하루 코디해보기",
                missionCompletedAt = "26.06.20",
                matchResult = MissionMatchResult(
                    matchPreferenceTypeTitle = "나와의 적합도",
                    matchRoundCount = 16,
                    matchCompletedAt = "26.06.10"
                ),
                satisfactionScore = 1.0f
            )
        )
    }
}