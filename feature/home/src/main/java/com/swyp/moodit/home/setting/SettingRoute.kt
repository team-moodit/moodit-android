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
                onTermsClick = { viewModel.sendIntent(SettingContract.Intent.OnTermsClick) },
                onPrivacyPolicyClick = { viewModel.sendIntent(SettingContract.Intent.OnPrivacyPolicyClick) },
                onFeedbackClick = { viewModel.sendIntent(SettingContract.Intent.OnFeedbackClick) },
                onShowLogOutDialog = { viewModel.sendIntent(SettingContract.Intent.ShowLogOutDialog) },
                onShowDeleteAccountDialog = { viewModel.sendIntent(SettingContract.Intent.ShowDeleteAccountDialog) },
                onDismissDialog = { viewModel.sendIntent(SettingContract.Intent.DismissDialog) },
                onConfirmLogOutClick = { viewModel.sendIntent(SettingContract.Intent.ConfirmLogOut) },
                onConfirmDeleteAccountClick = { viewModel.sendIntent(SettingContract.Intent.ConfirmDeleteAccount) },
                onConfirmCompleteDeleteAccountClick = { viewModel.sendIntent(SettingContract.Intent.ConfirmCompleteDeleteAccount) },
                uiState = uiState
            )
        }
    }
}