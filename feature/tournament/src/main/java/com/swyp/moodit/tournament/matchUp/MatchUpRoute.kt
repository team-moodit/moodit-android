package com.swyp.moodit.tournament.matchUp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MatchUpRoute(
    viewModel: MatchUpViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToTournamentResult: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is MatchUpContract.SideEffect.NavigateToResult -> {
                    navigateToTournamentResult(sideEffect.winnerPhotoId)
                }

                is MatchUpContract.SideEffect.NavigateBack -> {}

                is MatchUpContract.SideEffect.ShowSnackbar -> {
                    onShowSnackbar(sideEffect.message, null)
                }
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
            MatchUpScreen(
                uiState = uiState,
                onCandidateSelect = { candidate ->
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
                }
            )
        }
    }
}