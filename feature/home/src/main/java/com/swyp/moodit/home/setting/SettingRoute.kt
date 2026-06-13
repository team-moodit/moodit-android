package com.swyp.moodit.home.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SettingContract.SideEffect.NavigateToLogin -> navigateToLogin()
                is SettingContract.SideEffect.ShowSnackbar -> onShowSnackbar(
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
            SettingScreen(
                onLogOutClick = { viewModel.sendIntent(SettingContract.Intent.OnLogOutClick) },
                onDeleteAccountClick = { viewModel.sendIntent(SettingContract.Intent.OnDeleteAccountClick) }
            )
        }
    }
}