package com.swyp.moodit.auth.userInfo

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
import com.swyp.moodit.analytics.AnalyticsEvent
import com.swyp.moodit.analytics.LocalAnalyticsHelper
import com.swyp.moodit.analytics.Param
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun InputNicknameRoute(
    viewModel: InputNicknameViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToMain: () -> Unit,
    navigateToSetting: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val analyticsHelper = LocalAnalyticsHelper.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is InputNicknameContract.SideEffect.NavigateToSetting -> navigateToSetting()
                is InputNicknameContract.SideEffect.NavigateToMain -> {
                    analyticsHelper.logEvent(
                        AnalyticsEvent(
                            type = "set_nickname_success",
                            extras = listOf(
                                Param("user_type", "new_user")
                            )
                        )
                    )
                    navigateToMain()
                }

                is InputNicknameContract.SideEffect.ShowSnackbar -> onShowSnackbar(
                    sideEffect.message,
                    null
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        analyticsHelper.logEvent(
            AnalyticsEvent(
                type = AnalyticsEvent.Types.SCREEN_VIEW,
                extras = listOf(Param(Param.Keys.SCREEN_NAME, "CreateNameScreen"))
            )
        )
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
            InputNicknameScreen(
                uiState = uiState,
                onNicknameChange = {
                    viewModel.sendIntent(
                        InputNicknameContract.Intent.OnNicknameChange(
                            it
                        )
                    )
                },
                onConfirmClick = { viewModel.sendIntent(InputNicknameContract.Intent.OnConfirmClick) }
            )
        }
    }
}