package com.swyp.moodit.onboard.selectTaste

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun SelectTasteRoute(
    viewModel: SelectTasteViewModel = hiltViewModel(),
    navigateToReport: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SelectTasteContract.SideEffect.NavigateToReport -> navigateToReport()
            }
        }
    }

    SelectTasteScreen(
        onNextClick = { viewModel.sendIntent(SelectTasteContract.Intent.OnNextClick) }
    )
}