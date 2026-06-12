package com.swyp.moodit.onboard.route

import androidx.compose.runtime.Composable
import com.swyp.moodit.onboard.ui.SaveTasteScreen
import com.swyp.moodit.onboard.ui.SelectTasteScreen

@Composable
fun SelectTasteRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToReport: () -> Unit
) {
    SelectTasteScreen(
        onShowSnackbar = onShowSnackbar,
        onNextClick = navigateToReport
    )
}