package com.swyp.moodit.tournament.inProgressDetail

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swyp.moodit.analytics.AnalyticsEvent
import com.swyp.moodit.analytics.LocalAnalyticsHelper
import com.swyp.moodit.analytics.Param
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun InProgressTournamentDetailRoute(
    viewModel: InProgressTournamentDetailViewModel = hiltViewModel(),
    navigateToMatchUp: (Long, Boolean) -> Unit,
    navigateToMoodMatchResult: (Long) -> Unit,
    navigateToTournamentMain: () -> Unit,
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean
) {
    val analyticsHelper = LocalAnalyticsHelper.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is InProgressTournamentDetailContract.SideEffect.ShowSnackbar -> onShowSnackbar(
                    sideEffect.message,
                    null
                )

                is InProgressTournamentDetailContract.SideEffect.NavigateToMatchUp -> {
                    navigateToMatchUp(sideEffect.tournamentId, sideEffect.isStarted)
                }

                is InProgressTournamentDetailContract.SideEffect.NavigateToMoodMatchResult -> {
                    navigateToMoodMatchResult(sideEffect.matchResultId)
                }

                is InProgressTournamentDetailContract.SideEffect.NavigateToTournament -> {
                    navigateToTournamentMain()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        analyticsHelper.logEvent(
            AnalyticsEvent(
                type = AnalyticsEvent.Types.SCREEN_VIEW,
                extras = listOf(Param(Param.Keys.SCREEN_NAME, "MatchDetailInProgressScreen"))
            )
        )
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
            InProgressTournamentDetailScreen(
                uiState = uiState,
                onResumeClick = { viewModel.sendIntent(InProgressTournamentDetailContract.Intent.OnResumeTournamentClick) },
                onDeleteClick = { viewModel.sendIntent(InProgressTournamentDetailContract.Intent.OnDeleteTournamentClick) },
                onDeleteCompleteClick = { viewModel.sendIntent(InProgressTournamentDetailContract.Intent.OnDeleteTournamentCompleteClick) },
                onDeleteDialogShowChange = {
                    viewModel.sendIntent(
                        InProgressTournamentDetailContract.Intent.OnDeleteDialogShowChange(
                            it
                        )
                    )
                },
                onDeleteCompleteDialogShowChange = {
                    viewModel.sendIntent(
                        InProgressTournamentDetailContract.Intent.OnDeleteCompleteDialogShowChange(
                            it
                        )
                    )
                }
            )
        }
    }
}