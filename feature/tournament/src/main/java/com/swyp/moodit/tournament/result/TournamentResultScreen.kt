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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.component.button.MooditSelectableButton
import com.swyp.moodit.designsystem.theme.MooditTheme

@Composable
fun TournamentResultScreen(
    onMissionDetailClick: () -> Unit,
    onSelectMission: (Long) -> Unit,
    uiState: TournamentResultContract.State
) {
    MooditScaffold(
        bottomBar = {
            MooditFilledButton(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                onClick = { onMissionDetailClick() },
                enabled = uiState.selectedMission != null,
                text = "미션 확인하러 가기"
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            AsyncImage(
                model = "https://picsum.photos/200/300",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
                    .aspectRatio(0.82f)
                    .clip(RoundedCornerShape(16.dp)),
                contentDescription = "img_result",
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "약속 날 입고 갈 옷",
                style = MooditTheme.typography.h3,
                color = MooditTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(MooditTheme.colors.onPrimary, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MooditTheme.colors.primary.copy(0.1f))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.warning_diamond),
                        contentDescription = "icon_mood_result",
                        tint = MooditTheme.colors.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "이번 무드매치는\n나와의 적합도를 가장 중요하게 생각했어요",
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
                text = "미션을 통해\n내가 중요하게 생각하는 기준을 알아볼까요?",
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
                        content = mission.title,
                        isSelected = mission.id == uiState.selectedMission,
                        onItemClick = { onSelectMission(mission.id) }
                    )
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