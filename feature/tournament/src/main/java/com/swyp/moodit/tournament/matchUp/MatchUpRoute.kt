package com.swyp.moodit.tournament.matchUp

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun MatchUpRoute(
    viewModel: MatchUpViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToTournamentResult: (Long) -> Unit,
    navigateToHome: () -> Unit
) {
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
        viewModel.sendIntent(MatchUpContract.Intent.LoadMatchUpInfo)
    }

    BackHandler {
        viewModel.sendIntent(MatchUpContract.Intent.OnExitClick)
    }

    /*when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "내 취향을 찾기 위한\n무드매치를 준비하고 있어요", textAlign = TextAlign.Center)
                    CircularProgressIndicator()
                }
            }
        } */

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