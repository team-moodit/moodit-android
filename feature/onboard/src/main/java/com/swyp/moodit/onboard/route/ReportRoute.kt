package com.swyp.moodit.onboard.route

import androidx.compose.runtime.Composable
import com.swyp.moodit.onboard.ui.ReportScreen
import com.swyp.moodit.onboard.ui.SaveTasteScreen

@Composable
fun ReportRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToLogin: () -> Unit
) {
    ReportScreen(
        onShowSnackbar = onShowSnackbar,
        onStartClick = navigateToLogin
    )
}