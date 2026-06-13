package com.swyp.moodit.home.missionDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swyp.moodit.home.createRound.CreateRoundScreen
import com.swyp.moodit.home.createRound.CreateRoundViewModel

@Composable
fun MissionDetailRoute(
    viewModel: MissionDetailViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
            MissionDetailScreen(uiState)
        }
    }
}