package com.swyp.moodit.round.route

import androidx.compose.runtime.Composable
import com.swyp.moodit.round.RoundMainScreen

@Composable
fun RoundMainRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    RoundMainScreen(onShowSnackbar = onShowSnackbar)
}