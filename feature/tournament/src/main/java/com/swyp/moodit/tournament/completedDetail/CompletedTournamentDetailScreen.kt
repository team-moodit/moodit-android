package com.swyp.moodit.tournament.completedDetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.model.MissionState
import com.swyp.moodit.tournament.completedDetail.component.CompletedTournamentTabRow
import com.swyp.moodit.tournament.completedDetail.component.MissionTabContent
import com.swyp.moodit.tournament.completedDetail.component.MoodMatchTabContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedTournamentDetailScreen(
    uiState: CompletedTournamentDetailContract.State,
    onTabClick: (CompletedTournamentTab) -> Unit,
    onMissionDeleteClick: () -> Unit
) {
    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CompletedTournamentTabRow(
                selectedTab = uiState.selectedTab, onTabClick = onTabClick
            )
        },
        bottomBar = {
            if (uiState.selectedTab == CompletedTournamentTab.MISSION) {
                when (uiState.mission.missionState) {
                    MissionState.IN_PROGRESS -> MooditFilledButton(
                        modifier = Modifier.padding(
                            horizontal = 16.dp
                        ), onClick = {}, text = "미션을 완료했어요"
                    )

                    MissionState.COMPLETED -> MooditFilledButton(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = {},
                        text = "만족도 입력하기"
                    )

                    else -> null
                }
            }
        }
    ) { innerPadding ->
        when (uiState.selectedTab) {
            CompletedTournamentTab.MOOD_MATCH -> MoodMatchTabContent(innerPadding, uiState)
            CompletedTournamentTab.MISSION -> MissionTabContent(
                onMissionDeleteClick = onMissionDeleteClick,
                innerPadding,
                uiState
            )
        }
    }
}