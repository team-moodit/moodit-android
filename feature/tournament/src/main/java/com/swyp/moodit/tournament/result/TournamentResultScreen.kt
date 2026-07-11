package com.swyp.moodit.tournament.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swyp.moodit.common.util.TextUtil
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.component.button.MooditSelectableButton
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.MissionSuggestion
import com.swyp.moodit.model.PreferenceResultType

@Composable
fun TournamentResultScreen(
    onMissionDetailClick: () -> Unit,
    onSelectMission: (MissionSuggestion) -> Unit,
    uiState: TournamentResultContract.State
) {
    MooditScaffold(
        bottomBar = {
            MooditFilledButton(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                onClick = { onMissionDetailClick() },
                enabled = uiState.moodMatchResult.preferenceResultType == PreferenceResultType.TYPE_AND_DETAIL || uiState.moodMatchResult.missionSuggestions.size == 1 || uiState.selectedMission != null,
                text = "미션 확인하러 가기"
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp, end = 48.dp, top = 32.dp, bottom = 16.dp)
                    .aspectRatio(264f / 352f)
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.BottomCenter
            ) {
                AsyncImage(
                    model = uiState.moodMatchResult.matchResult.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = ColorPainter(Color.Gray)
                )
                Surface(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Black.copy(alpha = 0.5f)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 9.dp)
                            .clip(RoundedCornerShape(16.dp)),
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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = uiState.moodMatchResult.matchResult.matchTitle,
                style = MooditTheme.typography.h3, color = MooditTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(MooditTheme.colors.onPrimary, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val iconId =
                    if (uiState.moodMatchResult.preferenceResultType == PreferenceResultType.TIE) {
                        R.drawable.face_sad_tear
                    } else {
                        R.drawable.reward_stars
                    }

                val findTasteMessage =
                    if (uiState.moodMatchResult.preferenceResultType == PreferenceResultType.TIE)
                        "이번 무드매치는\n뚜렷한 취향의 기준이 없었어요"
                    else {
                        "이번 무드매치는\n${uiState.moodMatchResult.matchResult.matchPreferenceTypeTitle}${
                            TextUtil.attachParticle(
                                uiState.moodMatchResult.matchResult.matchPreferenceTypeTitle
                            )
                        } 가장 중요하게 생각했어요"
                    }
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            color = if (uiState.moodMatchResult.preferenceResultType == PreferenceResultType.TIE)
                                MooditTheme.colors.surfaceContainer
                            else
                                MooditTheme.colors.primary.copy(
                                    0.1f
                                )
                        )
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(iconId),
                        contentDescription = "icon_mood_result",
                        tint = if (uiState.moodMatchResult.preferenceResultType == PreferenceResultType.TIE)
                            MooditTheme.colors.onTertiary
                        else
                            MooditTheme.colors.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = findTasteMessage,
                    color = MooditTheme.colors.tertiary,
                    style = MooditTheme.typography.b3Medium
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                thickness = 8.dp,
                color = MooditTheme.colors.onPrimary
            )

            Text(
                text = "미션을 통해 내가 중요하게\n생각하는 기준을 알아볼까요?",
                color = MooditTheme.colors.onPrimaryContainer,
                style = MooditTheme.typography.b1Large,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(R.drawable.warning_diamond),
                    modifier = Modifier.size(20.dp),
                    tint = MooditTheme.colors.primary,
                    contentDescription = "icon_warning_diamond"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "취향의 기준은 한 번에 만들어지지 않을 수 있어요",
                    style = MooditTheme.typography.b3Medium,
                    color = MooditTheme.colors.textSecondary
                )
            }

            if (uiState.moodMatchResult.preferenceResultType != PreferenceResultType.TYPE_AND_DETAIL || uiState.moodMatchResult.missionSuggestions.size == 1) {
                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    uiState.moodMatchResult.missionSuggestions.forEach { mission ->
                        MooditSelectableButton(
                            content = mission.title.replace("\n", ""),
                            isSelected = mission.id == uiState.selectedMission?.id,
                            onItemClick = { onSelectMission(mission) })
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview
@Composable
fun TournamentResultScreenPreview() {
    MooditTheme {
        TournamentResultScreen(
            onMissionDetailClick = {},
            onSelectMission = { },
            uiState = TournamentResultContract.State()
        )
    }
}