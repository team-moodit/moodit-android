package com.swyp.moodit.home.route

import androidx.compose.runtime.Composable
import com.swyp.moodit.home.ui.HomeMainScreen

@Composable
fun HomeMainRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToSetting: () -> Unit
) {
    HomeMainScreen(onShowSnackbar = onShowSnackbar, onSettingClick = navigateToSetting)
}