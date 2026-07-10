package com.swyp.moodit.tournament.completedDetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swyp.moodit.common.util.DateUtil.toFormatDate
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditTag
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.MissionState
import com.swyp.moodit.tournament.completedDetail.CompletedTournamentDetailContract
import com.swyp.moodit.ui.component.mission.MissionInfoCard

@Composable
fun MissionTabContent(
    onMissionDeleteClick: () -> Unit,
    innerPadding: PaddingValues,
    uiState: CompletedTournamentDetailContract.State
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
            .padding(start = 16.dp, end = 16.dp, top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp)
                .aspectRatio(0.82f)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = uiState.mission.matchResult.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                error = ColorPainter(Color.Gray)
            )
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(100.dp),
                color = Color.Black.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 9.dp)
                        .clip(RoundedCornerShape(100.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(R.drawable.star_filled),
                        contentDescription = "icon_tag",
                        tint = Color.White
                    )

                    Text(
                        text = "${uiState.nickname}님이 픽한 취향",
                        color = MooditTheme.colors.onPrimaryContainer,
                        style = MooditTheme.typography.caption
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        MooditTag(
            content = when (uiState.mission.missionState) {
                MissionState.IN_PROGRESS -> "진행중"
                MissionState.COMPLETED -> "완료"
                MissionState.REVIEWED -> "완료"
            }
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = uiState.mission.missionTitle,
            textAlign = TextAlign.Center,
            style = MooditTheme.typography.h2,
            color = MooditTheme.colors.onBackground
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MissionInfoCard(
                modifier = Modifier.weight(1f),
                title = "무드매치 완료 날짜",
                content = uiState.mission.matchResult.matchCompletedAt.toFormatDate()
            )
            val missionCompletedAt = uiState.mission.missionCompletedAt.toFormatDate()
            MissionInfoCard(
                modifier = Modifier.weight(1f),
                title = "미션 완료 날짜",
                content = missionCompletedAt.ifEmpty { "-" }
            )
        }

        when (uiState.mission.missionState) {
            MissionState.IN_PROGRESS -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .clickable { onMissionDeleteClick() }
                        .padding(vertical = 20.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {

                    Text(
                        text = "미션 삭제하기",
                        color = MooditTheme.colors.borderDefault,
                        style = MooditTheme.typography.b3Medium
                    )
                    Icon(
                        painter = painterResource(R.drawable.chevron_right),
                        contentDescription = "icon_chevron_right",
                        modifier = Modifier.size(20.dp),
                        tint = MooditTheme.colors.onSurface
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            MissionState.COMPLETED -> {
                Text(
                    text = "만족도 평가",
                    style = MooditTheme.typography.b2Medium,
                    color = MooditTheme.colors.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(16.dp))
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

            MissionState.REVIEWED -> {
                Text(
                    text = "만족도 평가",
                    style = MooditTheme.typography.b2Medium,
                    color = MooditTheme.colors.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(16.dp))

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
                        text = "${uiState.mission.satisfactionScore}점",
                        color = MooditTheme.colors.primary,
                        style = MooditTheme.typography.b3Medium
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun MissionTabContentPreview() {
    MooditTheme {
        MissionTabContent(
            onMissionDeleteClick = {},
            innerPadding = PaddingValues(),
            uiState = CompletedTournamentDetailContract.State(nickname = "비비")
        )
    }
}