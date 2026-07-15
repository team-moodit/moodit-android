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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.swyp.moodit.analytics.AnalyticsEvent
import com.swyp.moodit.analytics.LocalAnalyticsHelper
import com.swyp.moodit.analytics.Param
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun SettingRoute(
    viewModel: SettingViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToLogin: () -> Unit,
    navigateToInputNickname: (Boolean) -> Unit
) {
    val analyticsHelper = LocalAnalyticsHelper.current
    val uiState by viewModel.uiState.collectAsState()
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(Unit) {
        viewModel.sendIntent(SettingContract.Intent.LoadUserInfo)
    }

    LaunchedEffect(Unit) {
        analyticsHelper.logEvent(
            AnalyticsEvent(
                type = AnalyticsEvent.Types.SCREEN_VIEW,
                extras = listOf(Param(Param.Keys.SCREEN_NAME, "SettingScreen"))
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SettingContract.SideEffect.NavigateToLogin -> navigateToLogin()
                is SettingContract.SideEffect.NavigateToInputNickname -> navigateToInputNickname(sideEffect.isEditMode)
                is SettingContract.SideEffect.ShowSnackbar -> onShowSnackbar(
                    sideEffect.message,
                    null
                )
                is SettingContract.SideEffect.NavigateToUrl -> uriHandler.openUri(sideEffect.url)
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
                onNicknameClick = { viewModel.sendIntent(SettingContract.Intent.OnNicknameClick) },
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