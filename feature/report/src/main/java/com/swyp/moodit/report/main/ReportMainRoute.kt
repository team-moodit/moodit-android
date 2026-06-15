package com.swyp.moodit.report.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ReportMainRoute(
    viewModel: ReportMainViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ReportMainContract.SideEffect.ShowSnackbar -> {
                    onShowSnackbar(sideEffect.message, null)
                }
            }
        }
    }

    ReportMainScreen(
        uiState = uiState,
        onTabClick = { viewModel.sendIntent(ReportMainContract.Intent.SelectTab(it)) }
    )
}