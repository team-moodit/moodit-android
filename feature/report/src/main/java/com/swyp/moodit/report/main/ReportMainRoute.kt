package com.swyp.moodit.report.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun ReportMainRoute(
    viewModel: ReportMainViewModel = hiltViewModel(),
    navigateToSetting: () -> Unit,
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ReportMainContract.SideEffect.ShowSnackbar -> {
                    onShowSnackbar(sideEffect.message, null)
                }

                is ReportMainContract.SideEffect.NavigateToSetting -> {
                    navigateToSetting()
                }
            }
        }
    }

    ReportMainScreen(
        uiState = uiState,
        onTabClick = { viewModel.sendIntent(ReportMainContract.Intent.SelectTab(it)) },
        onSettingClick = { viewModel.sendIntent(ReportMainContract.Intent.OnSettingClick) }
    )
}