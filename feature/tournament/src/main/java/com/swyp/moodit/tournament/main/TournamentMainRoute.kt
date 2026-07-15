package com.swyp.moodit.tournament.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.swyp.moodit.analytics.AnalyticsEvent
import com.swyp.moodit.analytics.LocalAnalyticsHelper
import com.swyp.moodit.analytics.Param
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.model.tournament.InProgressMatchState

@Composable
fun TournamentMainRoute(
    viewModel: TournamentMainViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToInProgressTournamentDetail: (Long, Long, InProgressMatchState) -> Unit,
    navigateToCompletedTournamentDetail: (Long, Long) -> Unit,
    navigateToSetting: () -> Unit
) {
    val analyticsHelper = LocalAnalyticsHelper.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val inProgressTournaments = uiState.inProgressTournaments.collectAsLazyPagingItems()
    val completedTournaments = uiState.completedTournaments.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is TournamentMainContract.SideEffect.ShowSnackbar -> onShowSnackbar(
                    sideEffect.message,
                    null
                )

                is TournamentMainContract.SideEffect.NavigateToInProgressTournamentDetail -> {
                    navigateToInProgressTournamentDetail(sideEffect.tournamentId, sideEffect.matchResultId, sideEffect.matchState)
                }

                is TournamentMainContract.SideEffect.NavigateToCompletedTournamentDetail -> {
                    navigateToCompletedTournamentDetail(sideEffect.tournamentId, sideEffect.userMissionId)
                }

                is TournamentMainContract.SideEffect.NavigateToSetting -> {
                    navigateToSetting()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        analyticsHelper.logEvent(
            AnalyticsEvent(
                type = AnalyticsEvent.Types.SCREEN_VIEW,
                extras = listOf(Param(Param.Keys.SCREEN_NAME, "TapMatchScreen"))
            )
        )
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        inProgressTournaments.refresh()
        completedTournaments.refresh()
    }


    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }

        else -> {
            TournamentMainScreen(
                uiState = uiState,
                inProgressTournaments = inProgressTournaments,
                completedTournaments = completedTournaments,
                onInProgressTournamentClick = { tournamentId, matchResultId, matchState ->
                    viewModel.sendIntent(
                        TournamentMainContract.Intent.OnInProgressTournamentClick(tournamentId, matchResultId, matchState)
                    )
                },
                onCompletedTournamentClick = { id, userMissionId ->
                    viewModel.sendIntent(
                        TournamentMainContract.Intent.OnCompletedTournamentClick(id, userMissionId)
                    )
                },
                onSettingClick = {
                    viewModel.sendIntent(TournamentMainContract.Intent.OnSettingClick)
                }
            )
        }
    }
}