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
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun MissionDetailRoute(
    viewModel: MissionDetailViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToReportReady: () -> Unit,
    navigateToHome: () -> Unit
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
                is MissionDetailContract.SideEffect.NavigateToHome -> navigateToHome()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sendIntent(MissionDetailContract.Intent.LoadMissionDetail)
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
                onTryButtonClick = { viewModel.sendIntent(MissionDetailContract.Intent.OnTryButtonClick) },
                onCompleteClick = { viewModel.sendIntent(MissionDetailContract.Intent.OnCompleteClick) },
                onSatisfactionShowChange = {
                    viewModel.sendIntent(
                        MissionDetailContract.Intent.OnSatisfactionShowChange(
                            it
                        )
                    )
                },
                onFeedbackShowChange = {
                    viewModel.sendIntent(
                        MissionDetailContract.Intent.OnFeedbackShowChange(
                            it
                        )
                    )
                },
                onSliderRatingChange = {
                    viewModel.sendIntent(
                        MissionDetailContract.Intent.OnSliderRatingChange(
                            it
                        )
                    )
                },
                onToggleFeedbackOption = {
                    viewModel.sendIntent(
                        MissionDetailContract.Intent.ToggleFeedbackOption(
                            it
                        )
                    )
                },
            )
        }
    }
}