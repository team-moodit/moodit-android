package com.swyp.moodit.tournament.result

import androidx.activity.compose.BackHandler
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
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.model.MissionStatus

@Composable
fun TournamentResultRoute(
    viewModel: TournamentResultViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToMissionDetail: (Long, MissionStatus) -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is TournamentResultContract.SideEffect.ShowSnackbar -> onShowSnackbar(
                    sideEffect.message,
                    null
                )

                is TournamentResultContract.SideEffect.NavigateToMissionDetail -> navigateToMissionDetail(
                    sideEffect.missionId,
                    sideEffect.status
                )

                is TournamentResultContract.SideEffect.NavigateToHome -> navigateToHome()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sendIntent(TournamentResultContract.Intent.LoadResult)
        viewModel.sendIntent(TournamentResultContract.Intent.LoadUserInfo)
    }

    BackHandler {
        viewModel.sendIntent(TournamentResultContract.Intent.OnExitClick)
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
            TournamentResultScreen(
                onMissionDetailClick = { viewModel.sendIntent(TournamentResultContract.Intent.OnMissionDetailClick) },
                onSelectMission = {
                    viewModel.sendIntent(
                        TournamentResultContract.Intent.OnMissionSelect(
                            it
                        )
                    )
                },
                uiState = uiState
            )
        }
    }
}