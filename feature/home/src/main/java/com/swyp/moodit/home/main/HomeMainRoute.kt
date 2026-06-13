package com.swyp.moodit.home.main

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
fun HomeMainRoute(
    viewModel: HomeMainViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToSetting: () -> Unit,
    navigateToCreateRound: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is HomeMainContract.SideEffect.NavigateToSetting -> navigateToSetting()
                is HomeMainContract.SideEffect.NavigateToCreateRound -> navigateToCreateRound()
                is HomeMainContract.SideEffect.ShowSnackbar -> onShowSnackbar(
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
                CircularProgressIndicator()
            }
        }

        else -> {
            HomeMainScreen(
                onSettingClick = { viewModel.sendIntent(HomeMainContract.Intent.OnSettingClick) },
                onCreateRoundClick = { viewModel.sendIntent(HomeMainContract.Intent.OnCreateRoundClick) })
        }
    }
}