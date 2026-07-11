package com.swyp.moodit.tournament.completedDetail.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.tournament.completedDetail.CompletedTournamentTab

@Composable
fun CompletedTournamentTabRow(
    selectedTab: CompletedTournamentTab, onTabClick: (CompletedTournamentTab) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = MooditTheme.colors.background
    ) {
        PrimaryTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = selectedTab.ordinal,
            containerColor = MooditTheme.colors.background,
            contentColor = MooditTheme.colors.onPrimaryContainer,
            divider = {
                HorizontalDivider(
                    thickness = 1.dp, color = MooditTheme.colors.onPrimary
                )
            },
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(selectedTab.ordinal)
                        .padding(horizontal = 56.dp),
                    width = Dp.Unspecified,
                    height = 2.dp,
                    color = MooditTheme.colors.primary
                )
            }) {
            CompletedTournamentTab.entries.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTab.ordinal == index,
                    onClick = { onTabClick(tab) },
                    text = {
                        Text(text = tab.tabName, style = MooditTheme.typography.h4)
                    },
                    selectedContentColor = MooditTheme.colors.onPrimaryContainer,
                    unselectedContentColor = MooditTheme.colors.borderDefault,
                )
            }
        }
    }
}

@Preview
@Composable
fun CompletedTournamentTabRowPreview() {
    MooditTheme {
        CompletedTournamentTabRow(
            selectedTab = CompletedTournamentTab.MOOD_MATCH,
            onTabClick = {}
        )

    }
}