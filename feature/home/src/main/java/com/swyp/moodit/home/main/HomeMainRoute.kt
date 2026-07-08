package com.swyp.moodit.home.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.swyp.moodit.designsystem.component.MooditDialog
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.designsystem.component.button.MooditFilledButton
import com.swyp.moodit.designsystem.theme.MooditTheme
import com.swyp.moodit.model.MissionStatus

@Composable
fun HomeMainRoute(
    viewModel: HomeMainViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToSetting: () -> Unit,
    navigateToCreateTournament: () -> Unit,
    navigateToMissionDetail: (Long, MissionStatus) -> Unit,
    navigateToMatchUp: (Long, Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val inProgressMissions = uiState.inProgressMissions.collectAsLazyPagingItems()
    val completedMissions = uiState.completedMissions.collectAsLazyPagingItems()
    val feedbackSubMittedMissions = uiState.feedbackSubMittedMissions.collectAsLazyPagingItems()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        inProgressMissions.refresh()
        completedMissions.refresh()
        feedbackSubMittedMissions.refresh()
        viewModel.sendIntent(HomeMainContract.Intent.CheckOnGoingTournament)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeMainContract.SideEffect.NavigateToSetting -> navigateToSetting()
                is HomeMainContract.SideEffect.NavigateToCreateTournament -> navigateToCreateTournament()
                is HomeMainContract.SideEffect.NavigateToMissionDetail -> navigateToMissionDetail(
                    sideEffect.missionId,
                    sideEffect.status
                )

                is HomeMainContract.SideEffect.ShowSnackbar -> onShowSnackbar(
                    sideEffect.message,
                    null
                )

                is HomeMainContract.SideEffect.NavigateToMatchUp -> navigateToMatchUp(
                    sideEffect.tournamentId,
                    false
                )
            }
        }
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
            HomeMainScreen(
                inProgressMissions = inProgressMissions,
                completedMissions = completedMissions,
                feedbackSubMittedMissions = feedbackSubMittedMissions,
                onSettingClick = { viewModel.sendIntent(HomeMainContract.Intent.OnSettingClick) },
                onCreateTournamentClick = { viewModel.sendIntent(HomeMainContract.Intent.OnCreateTournamentClick) },
                onMissionClick = { missionId ->
                    viewModel.sendIntent(
                        HomeMainContract.Intent.OnMissionClick(
                            missionId
                        )
                    )
                }
            )

            if (uiState.showResumeTournamentDialog) {
                MooditDialog(
                    title = "이어서 진행할 무드매치가 있어요",
                    description = "마지막 선택 지점부터 다시 시작해요."
                ) {
                    MooditFilledButton(
                        onClick = { viewModel.sendIntent(HomeMainContract.Intent.OnDismissTournamentDialog) },
                        modifier = Modifier.weight(1f),
                        text = "나중에",
                        containerColor = MooditTheme.colors.surfaceContainer,
                        contentColor = MooditTheme.colors.textSecondary
                    )
                    MooditFilledButton(
                        onClick = {
                            viewModel.sendIntent(
                                HomeMainContract.Intent.OnResumeTournamentClick(
                                    uiState.resumeTournamentId
                                )
                            )
                        },
                        modifier = Modifier.weight(1f),
                        text = "이어하기"
                    )
                }
            }
        }
    }
}