package com.swyp.moodit.onboard.report

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.swyp.moodit.analytics.AnalyticsEvent
import com.swyp.moodit.analytics.LocalAnalyticsHelper
import com.swyp.moodit.analytics.Param
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun ReportRoute(
    viewModel: ReportViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToLogin: () -> Unit
) {
    val analyticsHelper = LocalAnalyticsHelper.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ReportContract.SideEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    LaunchedEffect(Unit) {
        analyticsHelper.logEvent(
            AnalyticsEvent(
                type = AnalyticsEvent.Types.SCREEN_VIEW,
                extras = listOf(Param(Param.Keys.SCREEN_NAME, "OnBoardingScreen3"))
            )
        )
    }

    ReportScreen(
        onStartClick = { viewModel.sendIntent(ReportContract.Intent.OnStartClick) }
    )
}