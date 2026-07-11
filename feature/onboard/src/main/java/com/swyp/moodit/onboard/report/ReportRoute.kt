package com.swyp.moodit.onboard.report

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.swyp.moodit.designsystem.component.MooditSnackbarType

@Composable
fun ReportRoute(
    viewModel: ReportViewModel = hiltViewModel(),
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToLogin: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ReportContract.SideEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    ReportScreen(
        onStartClick = { viewModel.sendIntent(ReportContract.Intent.OnStartClick) }
    )
}