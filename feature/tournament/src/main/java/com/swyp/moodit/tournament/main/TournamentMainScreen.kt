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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.swyp.moodit.designsystem.R
import com.swyp.moodit.designsystem.component.MooditScaffold
import com.swyp.moodit.designsystem.component.MooditTopBar
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.tournament.component.CompletedTournamentItem
import com.swyp.moodit.tournament.component.InProgressTournamentItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TournamentMainScreen(
    uiState: TournamentMainContract.State,
    onInProgressTournamentClick: (Long) -> Unit,
    onCompletedTournamentClick: (Long) -> Unit,
    onSettingClick: () -> Unit
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
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 16.dp),
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
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (uiState.inProgressTournaments.isEmpty()) MooditTheme.colors.onSurface else MooditTheme.colors.primary,
                                shape = CircleShape
                            )
                    )

                    Text(
                        text = "${uiState.inProgressTournaments.size}개",
                        color = if (uiState.inProgressTournaments.isEmpty()) MooditTheme.colors.onSurface else MooditTheme.colors.primary,
                        style = MooditTheme.typography.caption
                    )
                }
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(
                    count = uiState.inProgressTournaments.size,
                    key = { index -> uiState.inProgressTournaments[index].id }
                ) { index ->
                    val inProgressTournament = uiState.inProgressTournaments[index]
                    InProgressTournamentItem(
                        inProgressTournament = inProgressTournament,
                        onTournamentClick = {
                            onInProgressTournamentClick(
                                inProgressTournament.id
                            )
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 20.dp),
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
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (uiState.inProgressTournaments.isEmpty()) MooditTheme.colors.onSurface else MooditTheme.colors.primary,
                                shape = CircleShape
                            )
                    )

                    Text(
                        text = "${uiState.completedTournaments.size}개",
                        color = if (uiState.inProgressTournaments.isEmpty()) MooditTheme.colors.onSurface else MooditTheme.colors.primary,
                        style = MooditTheme.typography.caption
                    )
                }
            }

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = innerPadding.calculateBottomPadding() + 40.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                columns = GridCells.Fixed(2)
            ) {
                items(count = uiState.completedTournaments.size, key = { index ->
                    uiState.completedTournaments[index].id
                }) { index ->
                    val completedTournament = uiState.completedTournaments[index]
                    CompletedTournamentItem(
                        completedTournament = completedTournament,
                        onTournamentClick = {
                            onCompletedTournamentClick(
                                completedTournament.id,
                            )
                        }
                    )
                }
            }
        }
    }
}