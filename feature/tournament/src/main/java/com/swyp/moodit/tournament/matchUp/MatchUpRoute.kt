package com.swyp.moodit.tournament.matchUp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun MatchUpRoute(
    viewModel: MatchUpViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToTournamentResult: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is MatchUpContract.SideEffect.NavigateToResult -> {
                    navigateToTournamentResult(sideEffect.winnerPhotoId)
                }

                is MatchUpContract.SideEffect.NavigateBack -> {
                    onShowSnackbar("무드 매치를 종료하시겠습니까?", null)
                }

                is MatchUpContract.SideEffect.ShowSnackbar -> {
                    onShowSnackbar(sideEffect.message, sideEffect.snackbarType)
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "내 취향을 찾기 위한\n무드매치를 준비하고 있어요", textAlign = TextAlign.Center)
                    CircularProgressIndicator()
                }
            }
        }

        else -> {
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
                onNextButtonClick = {
                    viewModel.sendIntent(MatchUpContract.Intent.OnNextButtonClick)
                }
            )
        }
    }
}