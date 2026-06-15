package com.swyp.moodit.report.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReportMainScreen(
    uiState: ReportMainContract.State,
    onTabClick: (ReportTab) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "취향 리포트")

        SecondaryTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = uiState.selectedTab.ordinal
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
                    OverallReviewContent(uiState = uiState)
                }

                ReportTab.SATISFACTION -> {
                    SatisfactionResultContent(uiState = uiState)
                }
            }
        }
    }
}

@Composable
fun OverallReviewContent(uiState: ReportMainContract.State) {
    Text(text = "당신은 00할 때 나와의 적합도를 가장 중요하게 봐요")
}

@Composable
fun SatisfactionResultContent(uiState: ReportMainContract.State) {
    Text(text = "만족도 결과")
}