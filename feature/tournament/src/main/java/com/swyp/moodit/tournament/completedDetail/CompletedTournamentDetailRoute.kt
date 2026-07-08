package com.swyp.moodit.tournament.completedDetail

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
import com.swyp.moodit.tournament.inProgressDetail.InProgressTournamentDetailContract
import com.swyp.moodit.tournament.inProgressDetail.InProgressTournamentDetailScreen
import com.swyp.moodit.tournament.inProgressDetail.InProgressTournamentDetailViewModel

@Composable
fun CompletedTournamentDetailRoute(
    viewModel: CompletedTournamentDetailViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CompletedTournamentDetailContract.SideEffect.ShowSnackbar -> onShowSnackbar(
                    sideEffect.message,
                    null
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
                    CircularProgressIndicator()
                }
            }
        }

        else -> {
            CompletedTournamentDetailScreen(
                uiState = uiState,
                onTabClick = { viewModel.sendIntent(CompletedTournamentDetailContract.Intent.SelectTab(it)) },
                onMissionDeleteClick = {}
            )
        }
    }
}