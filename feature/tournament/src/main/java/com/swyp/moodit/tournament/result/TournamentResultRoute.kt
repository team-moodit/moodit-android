package com.swyp.moodit.tournament.result

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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swyp.moodit.navigation.MissionStatus

@Composable
fun TournamentResultRoute(
    viewModel: TournamentResultViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToMissionDetail: (String, MissionStatus) -> Unit
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
                    Text(text = "토너먼트를 생성하고 있어요")
                    CircularProgressIndicator()
                }
            }
        }

        else -> {
            TournamentResultScreen(
                onMissionDetailClick = { viewModel.sendIntent(TournamentResultContract.Intent.OnMissionDetailClick) }
            )
        }
    }
}