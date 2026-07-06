package com.swyp.moodit.tournament.completedDetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedTournamentDetailScreen(
    uiState: CompletedTournamentDetailContract.State,
    onTabClick: (CompletedTournamentTab) -> Unit
) {
    MooditScaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (uiState.selectedTab == CompletedTournamentTab.MISSION) {
                MooditFilledButton(text = "미션을 완료했어요")
            }
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 16.dp,
                end = innerPadding.calculateEndPadding(LayoutDirection.Rtl) + 16.dp,
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            ),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                CompletedTournamentTabRow(
                    selectedTab = uiState.selectedTab,
                    onTabClick = onTabClick
                )
            }
        }
    }
}

@Composable
fun CompletedTournamentTabRow(
    selectedTab: CompletedTournamentTab,
    onTabClick: (CompletedTournamentTab) -> Unit
) {
    PrimaryTabRow(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding(),
        selectedTabIndex = selectedTab.ordinal,
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
                    .tabIndicatorOffset(selectedTab.ordinal)
                    .padding(horizontal = 56.dp),
                width = Dp.Unspecified,
                height = 2.dp,
                color = MooditTheme.colors.primary
            )
        }
    ) {
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

@Composable
fun CompletedTournamentContent() {
    Text(text = "CompletedTournamentScreen", style = MaterialTheme.typography.displayMedium)
}