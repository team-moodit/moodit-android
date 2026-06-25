package com.swyp.moodit.home.reportReady

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
fun ReportReadyRoute(
    viewModel: ReportReadyViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToReport: () -> Unit,
    navigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ReportReadyContract.SideEffect.ShowSnackbar -> onShowSnackbar(
                    sideEffect.message,
                    null
                )

                is ReportReadyContract.SideEffect.NavigateToReport -> navigateToReport()
                is ReportReadyContract.SideEffect.NavigateToHome -> navigateToHome()
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
            ReportReadyScreen(
                uiState = uiState,
                onNavigateReportClick = { viewModel.sendIntent(ReportReadyContract.Intent.OnNavigateReportClick) },
                onNavigateHomeClick = { viewModel.sendIntent(ReportReadyContract.Intent.OnNavigateHomeClick) }
            )
        }
    }
}