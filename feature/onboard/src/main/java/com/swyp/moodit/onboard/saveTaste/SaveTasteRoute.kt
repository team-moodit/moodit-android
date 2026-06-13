package com.swyp.moodit.onboard.saveTaste

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun SaveTasteRoute(
    viewModel: SaveTasteViewModel = hiltViewModel(),
    navigateToSelectTaste: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SaveTasteContract.SideEffect.NavigateToSelectTaste -> navigateToSelectTaste()
            }
        }
    }

    SaveTasteScreen(
        onNextClick = { viewModel.sendIntent(SaveTasteContract.Intent.OnNextClick) }
    )
}