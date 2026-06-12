package com.swyp.moodit.onboard.route

import androidx.compose.runtime.Composable
import com.swyp.moodit.onboard.ui.SaveTasteScreen

@Composable
fun SaveTasteRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToSelectTaste: () -> Unit
) {
    SaveTasteScreen(
        onShowSnackbar = onShowSnackbar,
        onNextClick = navigateToSelectTaste
    )
}