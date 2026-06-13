package com.swyp.moodit.home.missionDetail

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
fun MissionDetailRoute(
    viewModel: MissionDetailViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToReportReady: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is MissionDetailContract.SideEffect.ShowSnackbar -> onShowSnackbar(
                    sideEffect.message,
                    null
                )

                is MissionDetailContract.SideEffect.NavigateToReportReady -> navigateToReportReady()
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
            MissionDetailScreen(
                uiState = uiState,
                onCompleteClick = { viewModel.sendIntent(MissionDetailContract.Intent.OnCompleteClick) })
        }
    }
}