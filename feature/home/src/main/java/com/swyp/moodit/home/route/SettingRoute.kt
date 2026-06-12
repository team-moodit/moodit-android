package com.swyp.moodit.home.route

import androidx.compose.runtime.Composable
import com.swyp.moodit.home.ui.HomeMainScreen
import com.swyp.moodit.home.ui.SettingScreen

@Composable
fun SettingRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    SettingScreen(onShowSnackbar = onShowSnackbar)
}