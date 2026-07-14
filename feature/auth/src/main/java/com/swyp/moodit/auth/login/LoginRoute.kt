package com.swyp.moodit.auth.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swyp.moodit.analytics.AnalyticsEvent
import com.swyp.moodit.analytics.LocalAnalyticsHelper
import com.swyp.moodit.analytics.Param
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToMain: () -> Unit,
    navigateToInputNickname: (Boolean) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val analyticsHelper = LocalAnalyticsHelper.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is LoginContract.SideEffect.NavigateToMain -> {
                    val clickTimeStamp = System.currentTimeMillis()
                    analyticsHelper.logEvent(
                        AnalyticsEvent(
                            type = "login_success",
                            extras = listOf(
                                Param("user_type", "exist_user"),
                                Param("attempted_at", clickTimeStamp.toString())
                            )
                        )
                    )
                    navigateToMain()
                }

                is LoginContract.SideEffect.NavigateToInputNickname -> {
                    val clickTimeStamp = System.currentTimeMillis()
                    analyticsHelper.logEvent(
                        AnalyticsEvent(
                            type = "signUp_success",
                            extras = listOf(
                                Param("user_type", "new_user"),
                                Param("attempted_at", clickTimeStamp.toString())
                            )
                        )
                    )
                    navigateToInputNickname(sideEffect.isEditMode)
                }

                is LoginContract.SideEffect.ShowSnackbar -> onShowSnackbar(sideEffect.message, null)
            }
        }
    }

    LaunchedEffect(Unit) {
        analyticsHelper.logEvent(
            AnalyticsEvent(
                type = AnalyticsEvent.Types.SCREEN_VIEW,
                extras = listOf(Param(Param.Keys.SCREEN_NAME, "LoginScreen"))
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
            LoginScreen(
                onKakaoLoginClick = { viewModel.sendIntent(LoginContract.Intent.OnLoginClick(context)) }
            )
        }
    }
}