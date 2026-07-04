package com.swyp.moodit.report.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.Mission
import com.swyp.moodit.report.main.ReportMainContract
import com.swyp.moodit.ui.component.mission.MissionReviewedItemCard
import kotlinx.coroutines.flow.flowOf

@Composable
fun SatisfactionResultContent(
    modifier: Modifier = Modifier,
    uiState: ReportMainContract.State,
    onCreateMoodMatchClick: () -> Unit,
    onCheckMissionClick: () -> Unit,
    onMissionClick: (Long) -> Unit,
    feedbackSubMittedMissions: LazyPagingItems<Mission>
) {
    if (uiState.reportSummary.summary.totalMatchCount == 0L && uiState.reportSummary.summary.completedMissionCount == 0L) {
        ReportEmptyContent(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 78.dp),
            title = "아직 분석할 취향이 없어요",
            content = "무드매치로 취향을 고른 뒤\n가볍게 시작해보세요",
            buttonLabel = "새 무드매치 만들기",
            onButtonClick = { onCreateMoodMatchClick() }
        )
    }

    if (uiState.reportSummary.summary.totalMatchCount != 0L && uiState.reportSummary.summary.completedMissionCount == 0L) {
        ReportEmptyContent(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 78.dp),
            title = "직접 미션을 해보니 어땠나요?",
            content = "경험을 기록한 모든 미션을 \n" +
                    "한 눈에 보기 쉽게 정리해 드릴게요",
            buttonLabel = "미션 확인하기",
            onButtonClick = { onCheckMissionClick() })
    }
    if (uiState.reportSummary.summary.completedMissionCount != 0L) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 22.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(
                count = feedbackSubMittedMissions.itemCount,
                key = { index -> feedbackSubMittedMissions[index]?.userMissionId ?: index }
            ) { index ->
                val mission = feedbackSubMittedMissions[index]
                if (mission != null)
                    MissionReviewedItemCard(
                        modifier = Modifier.fillMaxWidth(),
                        mission = mission,
                        onClick = { onMissionClick(mission.userMissionId) })
            }
        }
    }
}


@Preview
@Composable
fun SatisfactionResultContentPreview() {
    val emptyMissionsFlow = remember {
        flowOf(PagingData.from(emptyList<Mission>()))
    }
    MooditTheme {
        SatisfactionResultContent(
            uiState = ReportMainContract.State(),
            onCreateMoodMatchClick = {},
            onCheckMissionClick = {},
            onMissionClick = {},
            feedbackSubMittedMissions = emptyMissionsFlow.collectAsLazyPagingItems()
        )
    }
}