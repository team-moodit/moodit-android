package com.swyp.moodit.report.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.report.main.ReportMainContract

@Composable
fun OverAllReviewContent(uiState: ReportMainContract.State, onCreateMoodMatchClick: () -> Unit) {
    if (uiState.reportSummary.summary.totalMatchCount == 0L) {
        ReportEmptyContent(
            modifier = Modifier.padding(top = 78.dp),
            title = "아직 분석할 취향이 없어요",
            content = "무드매치를 시작하고 사진을 선택해보세요\n" +
                    "선택이 쌓일수록 나만의 리포트가 만들어집니다",
            buttonLabel = "새 무드매치 만들기",
            onButtonClick = { onCreateMoodMatchClick() }
        )
    }
}

@Preview
@Composable
fun OverAllReviewContentPreview() {
    MooditTheme {
        OverAllReviewContent(
            uiState = ReportMainContract.State(),
            onCreateMoodMatchClick = {}
        )
    }
}