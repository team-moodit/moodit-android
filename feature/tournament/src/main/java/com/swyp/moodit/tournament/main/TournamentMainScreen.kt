package com.swyp.moodit.tournament.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.MooditTopBar
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.tournament.CompletedTournament
import com.swyp.moodit.model.tournament.InProgressMatchState
import com.swyp.moodit.model.tournament.InProgressTournament
import com.swyp.moodit.tournament.component.CompletedTournamentItem
import com.swyp.moodit.tournament.component.InProgressTournamentItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TournamentMainScreen(
    uiState: TournamentMainContract.State,
    inProgressTournaments: LazyPagingItems<InProgressTournament>,
    completedTournaments: LazyPagingItems<CompletedTournament>,
    onInProgressTournamentClick: (Long, Long, InProgressMatchState) -> Unit,
    onCompletedTournamentClick: (Long, Long) -> Unit,
    onSettingClick: () -> Unit
) {
    MooditScaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = innerPadding.calculateEndPadding(LayoutDirection.Rtl),
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            ),
            columns = GridCells.Fixed(2)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                MooditTopBar(
                    modifier = Modifier.padding(end = 8.dp),
                    textAlign = TextAlign.Center,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MooditTheme.colors.background,
                        titleContentColor = MooditTheme.colors.onBackground,
                        scrolledContainerColor = MooditTheme.colors.background
                    ),
                    title = {
                        Text(
                            text = "무드매치",
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

            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "무드매치 이어하기",
                            style = MooditTheme.typography.h4,
                            color = MooditTheme.colors.onPrimaryContainer
                        )
                        Row(
                            modifier = Modifier.wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(
                                        color = if (inProgressTournaments.itemCount == 0) MooditTheme.colors.onSurface else MooditTheme.colors.primary,
                                        shape = CircleShape
                                    )
                            )

                            Text(
                                text = "${inProgressTournaments.itemCount}개",
                                color = if (inProgressTournaments.itemCount == 0) MooditTheme.colors.onSurface else MooditTheme.colors.primary,
                                style = MooditTheme.typography.caption
                            )
                        }
                    }

                    if (inProgressTournaments.itemCount == 0) {
                        Spacer(modifier = Modifier.height(128.dp))
                    }

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(
                            count = inProgressTournaments.itemCount,
                            key = { index ->
                                inProgressTournaments[index]?.matchId ?: index
                            }
                        ) { index ->
                            val inProgressTournament = inProgressTournaments[index]
                            if (inProgressTournament != null) {
                                InProgressTournamentItem(
                                    inProgressTournament = inProgressTournament,
                                    onTournamentClick = {
                                        onInProgressTournamentClick(
                                            inProgressTournament.matchId,
                                            inProgressTournament.matchResultId,
                                            inProgressTournament.matchState
                                        )
                                    }
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "완료한 무드매치",
                            style = MooditTheme.typography.h4,
                            color = MooditTheme.colors.onPrimaryContainer
                        )
                        Row(
                            modifier = Modifier.wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(
                                        color = if (completedTournaments.itemCount == 0) MooditTheme.colors.onSurface else MooditTheme.colors.primary,
                                        shape = CircleShape
                                    )
                            )

                            Text(
                                text = "${completedTournaments.itemCount}개",
                                color = if (completedTournaments.itemCount == 0) MooditTheme.colors.onSurface else MooditTheme.colors.primary,
                                style = MooditTheme.typography.caption
                            )
                        }
                    }
                }
            }

            items(
                count = completedTournaments.itemCount,
                key = { index ->
                    completedTournaments[index]?.matchId ?: index
                }) { index ->
                val completedTournament = completedTournaments[index]
                val isLeft = index % 2 == 0
                if (completedTournament != null) {
                    Box(
                        modifier = Modifier.padding(
                            start = if (isLeft) 16.dp else 6.dp,
                            end = if (isLeft) 6.dp else 16.dp
                        )
                    ) {
                        CompletedTournamentItem(
                            completedTournament = completedTournament,
                            onTournamentClick = {
                                onCompletedTournamentClick(
                                    completedTournament.matchId,
                                    completedTournament.userMissionId
                                )
                            }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.statusBars)
                .background(MooditTheme.colors.background)
        )
    }
}