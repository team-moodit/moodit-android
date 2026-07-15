package com.swyp.moodit.tournament.matchUp

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swyp.moodit.analytics.AnalyticsEvent
import com.swyp.moodit.analytics.LocalAnalyticsHelper
import com.swyp.moodit.analytics.Param
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun MatchUpRoute(
    viewModel: MatchUpViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToTournamentResult: (Long) -> Unit,
    navigateToHome: () -> Unit
) {
    val analyticsHelper = LocalAnalyticsHelper.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is MatchUpContract.SideEffect.NavigateToResult -> {
                    navigateToTournamentResult(sideEffect.tournamentId)
                }

                is MatchUpContract.SideEffect.NavigateBack -> {
                    navigateToHome()
                }

                is MatchUpContract.SideEffect.ShowSnackbar -> {
                    onShowSnackbar(sideEffect.message, sideEffect.snackbarType)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        analyticsHelper.logEvent(
            AnalyticsEvent(
                type = AnalyticsEvent.Types.SCREEN_VIEW,
                extras = listOf(Param(Param.Keys.SCREEN_NAME, "MatchInProgressScreen"))
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.sendIntent(MatchUpContract.Intent.LoadMatchUpInfo)
    }

    BackHandler {
        viewModel.sendIntent(MatchUpContract.Intent.OnExitClick)
    }

    MatchUpScreen(
        uiState = uiState,
        onSelectCandidate = { candidate ->
            viewModel.sendIntent(
                MatchUpContract.Intent.OnCandidateSelect(
                    candidate
                )
            )
        },
        onReasonSelect = { reasonId ->
            viewModel.sendIntent(
                MatchUpContract.Intent.OnReasonSelect(
                    reasonId
                )
            )
        },
        onNextButtonClick = { viewModel.sendIntent(MatchUpContract.Intent.OnNextButtonClick) },
        onExitClick = {
            viewModel.sendIntent(MatchUpContract.Intent.OnExitClick)
        },
        onRetryClick = {
            viewModel.sendIntent(MatchUpContract.Intent.OnRetryClick)
        }
    )
}