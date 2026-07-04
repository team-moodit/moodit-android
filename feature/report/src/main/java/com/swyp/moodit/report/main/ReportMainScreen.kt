package com.swyp.moodit.report.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.MooditTopBar
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.Mission
import com.swyp.moodit.report.component.OverAllReviewContent
import com.swyp.moodit.report.component.SatisfactionResultContent
import com.swyp.moodit.ui.component.mission.MissionInfoCard
import kotlinx.coroutines.flow.flowOf

@Composable
fun ReportMainScreen(
    uiState: ReportMainContract.State,
    onTabClick: (ReportTab) -> Unit,
    onSettingClick: () -> Unit,
    onMissionClick: (Long) -> Unit,
    feedbackSubMittedMissions: LazyPagingItems<Mission>
) {
    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MooditTopBar(
                modifier = Modifier.padding(end = 8.dp),
                textAlign = TextAlign.Center,
                title = {
                    Text(
                        text = "리포트",
                        style = MooditTheme.typography.h2,
                        color = MooditTheme.colors.onPrimaryContainer
                    )
                },
                actionIcon = {
                    Image(
                        painter = painterResource(R.drawable.setting),
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onSettingClick() },
                        contentDescription = "icon_setting"
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "분석에 반영된 기록",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp, bottom = 12.dp),
                color = MooditTheme.colors.onPrimaryContainer,
                style = MooditTheme.typography.b1Medium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MissionInfoCard(
                    modifier = Modifier.weight(1f),
                    title = "무드매치 총 횟수",
                    content = "${uiState.reportSummary.summary.totalMatchCount}회"
                )
                MissionInfoCard(
                    modifier = Modifier.weight(1f),
                    title = "완료한 미션",
                    content = "${uiState.reportSummary.summary.completedMissionCount}회"
                )
            }

            PrimaryTabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                selectedTabIndex = uiState.selectedTab.ordinal,
                containerColor = MooditTheme.colors.background,
                contentColor = MooditTheme.colors.onPrimaryContainer,
                divider = {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MooditTheme.colors.onPrimary
                    )
                },
                indicator = {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(uiState.selectedTab.ordinal)
                            .padding(horizontal = 56.dp),
                        width = Dp.Unspecified,
                        height = 2.dp,
                        color = MooditTheme.colors.primary
                    )
                }
            ) {
                ReportTab.entries.forEachIndexed { index, tab ->
                    Tab(
                        selected = uiState.selectedTab.ordinal == index,
                        onClick = { onTabClick(tab) },
                        text = {
                            Text(text = tab.tabName)
                        }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (uiState.selectedTab) {
                    ReportTab.REPORT -> {
                        OverAllReviewContent(
                            uiState = uiState,
                            onCreateMoodMatchClick = {},
                            onCheckMissionClick = {}
                        )
                    }

                    ReportTab.SATISFACTION -> {
                        SatisfactionResultContent(
                            uiState = uiState,
                            onCreateMoodMatchClick = {},
                            onCheckMissionClick = {},
                            onMissionClick = onMissionClick,
                            feedbackSubMittedMissions = feedbackSubMittedMissions
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ReportMainScreenPreview() {
    val emptyMissionsFlow = remember {
        flowOf(PagingData.from(emptyList<Mission>()))
    }
    MooditTheme {
        ReportMainScreen(
            uiState = ReportMainContract.State(),
            onTabClick = {},
            onSettingClick = {},
            onMissionClick = {},
            feedbackSubMittedMissions = emptyMissionsFlow.collectAsLazyPagingItems()
        )
    }
}