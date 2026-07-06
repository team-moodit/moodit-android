package com.swyp.moodit.report.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.common.util.TextUtil
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.report.PreferenceDetail
import com.swyp.moodit.model.report.PreferenceReport
import com.swyp.moodit.model.report.ReportSummary
import com.swyp.moodit.model.report.SummaryCount
import com.swyp.moodit.report.main.ReportMainContract

@Composable
fun OverAllReviewContent(
    uiState: ReportMainContract.State,
    onCreateMoodMatchClick: () -> Unit,
    onCheckMissionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.reportSummary.summary.totalMatchCount == 0L) {
        ReportEmptyContent(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 78.dp),
            title = "아직 분석할 취향이 없어요",
            content = "무드매치를 시작하고 사진을 선택해보세요\n" +
                    "선택이 쌓일수록 나만의 리포트가 만들어집니다",
            buttonLabel = "새 무드매치 만들기",
            onButtonClick = { onCreateMoodMatchClick() }
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.padding(end = 6.dp),
                        text = "당신은",
                        style = MooditTheme.typography.b1Large,
                        color = MooditTheme.colors.onPrimaryContainer
                    )
                    Text(
                        text = "‘${uiState.reportSummary.preferenceReport.topPreference.title}‘",
                        style = MooditTheme.typography.b1Large,
                        color = MooditTheme.colors.primary
                    )
                    Text(
                        text = TextUtil.attachParticle(uiState.reportSummary.preferenceReport.topPreference.title),
                        style = MooditTheme.typography.b1Large,
                        color = MooditTheme.colors.onPrimaryContainer
                    )
                }
                Text(
                    text = "가장 중요하게 봐요",
                    style = MooditTheme.typography.b1Large,
                    color = MooditTheme.colors.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ReportPreferenceCard(
                totalMatchCount = uiState.reportSummary.preferenceReport.totalSelectionCount,
                topPreference = uiState.reportSummary.preferenceReport.topPreference,
                preferenceDistributions = uiState.top3Distributions
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                SatisfactionCard(
                    modifier = Modifier.blur(if (uiState.reportSummary.rateSummary.count == 0L) 6.dp else 0.dp),
                    satisfactionSummary = uiState.reportSummary.rateSummary
                )
                if (uiState.reportSummary.rateSummary.count == 0L) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "직접 미션을 해보니 어땠나요?",
                            style = MooditTheme.typography.b1Large,
                            color = MooditTheme.colors.onPrimaryContainer
                        )
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 80.dp),
                            onClick = onCheckMissionClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MooditTheme.colors.primary,
                                contentColor = MooditTheme.colors.onPrimary
                            ),
                            contentPadding = PaddingValues(horizontal = 57.dp, vertical = 12.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(),
                                text = "미션 확인하기",
                                color = MooditTheme.colors.onPrimary,
                                style = MooditTheme.typography.b1Medium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            ReportGuideDescription()

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Preview
@Composable
fun OverAllReviewContentPreview() {
    MooditTheme {
        OverAllReviewContent(
            uiState = ReportMainContract.State(
                reportSummary = ReportSummary(
                    summary = SummaryCount(
                        totalMatchCount = 12L
                    ),
                    preferenceReport = PreferenceReport(
                        topPreference = PreferenceDetail(
                            title = "나와의 적합도"
                        )
                    )
                )
            ),
            onCreateMoodMatchClick = {},
            onCheckMissionClick = {},
        )
    }
}