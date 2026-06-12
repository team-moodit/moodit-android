package com.swyp.moodit.home.route

import androidx.compose.runtime.Composable
import com.swyp.moodit.home.HomeMainScreen

@Composable
fun HomeMainRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    HomeMainScreen(onShowSnackbar = onShowSnackbar)
}