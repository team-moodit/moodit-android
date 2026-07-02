package com.swyp.moodit.report.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.report.main.ReportMainContract

@Composable
fun SatisfactionResultContent(
    uiState: ReportMainContract.State,
    onCreateMoodMatchClick: () -> Unit,
    onCheckMissionClick: () -> Unit
) {
    if (uiState.reportSummary.summary.totalMatchCount == 0L && uiState.reportSummary.summary.completedMissionCount == 0L) {
        ReportEmptyContent(
            modifier = Modifier.padding(top = 78.dp),
            title = "아직 분석할 취향이 없어요",
            content = "무드매치로 취향을 고른 뒤\n가볍게 시작해보세요",
            buttonLabel = "새 무드매치 만들기",
            onButtonClick = { onCreateMoodMatchClick() }
        )
    }

    if (uiState.reportSummary.summary.totalMatchCount != 0L && uiState.reportSummary.summary.completedMissionCount == 0L) {
        ReportEmptyContent(
            modifier = Modifier.padding(top = 78.dp),
            title = "직접 미션을 해보니 어땠나요?",
            content = "경험을 기록한 모든 미션을 \n" +
                    "한 눈에 보기 쉽게 정리해 드릴게요",
            buttonLabel = "미션 확인하기",
            onButtonClick = { onCheckMissionClick() })
    }
}


@Preview
@Composable
fun SatisfactionResultContentPreview() {
    MooditTheme {
        SatisfactionResultContent(
            uiState = ReportMainContract.State(),
            onCreateMoodMatchClick = {},
            onCheckMissionClick = {}

        )
    }
}