package com.swyp.moodit.report.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swyp.moodit.common.util.TextUtil
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.report.PreferenceDetail
import com.swyp.moodit.model.report.PreferenceReport
import com.swyp.moodit.model.report.ReportSummary
import com.swyp.moodit.model.report.ResultType
import com.swyp.moodit.model.report.SatisfactionSummary
import com.swyp.moodit.model.report.SummaryCount
import com.swyp.moodit.report.main.ReportMainContract

@Composable
fun OverAllReviewContent(
    uiState: ReportMainContract.State,
    onCreateMoodMatchClick: () -> Unit,
    onCheckMissionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.reportSummary.preferenceReport.resultType == ResultType.NONE) {
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.reportSummary.preferenceReport.resultType == ResultType.PREFERENCE_DETAIL ||
                uiState.reportSummary.preferenceReport.resultType == ResultType.PREFERENCE_ONLY
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 22.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 6.dp),
                            text = "${uiState.nickname}님은",
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
            } else if (uiState.reportSummary.preferenceReport.resultType == ResultType.PREFERENCE_TIE) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 22.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "${uiState.nickname}님은 아직 취향을 찾아가고 있어요\n무드매치를 더 진행해보세요",
                        style = MooditTheme.typography.b1Large,
                        color = MooditTheme.colors.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ReportPreferenceCard(
                totalMatchCount = uiState.reportSummary.preferenceReport.totalSelectionCount,
                preferenceReport = uiState.reportSummary.preferenceReport
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.reportSummary.rateSummary.count == 0L) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(328f / 150f),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.empty_report),
                        contentDescription = "image_empty_report",
                        modifier = Modifier.matchParentSize(),
                        contentScale = ContentScale.FillBounds
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "직접 미션을 해보니 어땠나요?",
                            style = MooditTheme.typography.b1Large,
                            color = MooditTheme.colors.onPrimaryContainer
                        )
                        Button(
                            modifier = Modifier.wrapContentWidth(),
                            onClick = onCheckMissionClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MooditTheme.colors.primary,
                                contentColor = MooditTheme.colors.onPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 12.dp)
                        ) {
                            Text(
                                modifier = Modifier.wrapContentWidth(),
                                text = "미션 확인하기",
                                color = MooditTheme.colors.onPrimary,
                                style = MooditTheme.typography.b1Medium,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            } else {
                SatisfactionCard(
                    satisfactionSummary = uiState.reportSummary.rateSummary
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

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
                        ),
                    ),
                    rateSummary = SatisfactionSummary(
                        count = 0L
                    )
                )
            ),
            onCreateMoodMatchClick = {},
            onCheckMissionClick = {},
        )
    }
}