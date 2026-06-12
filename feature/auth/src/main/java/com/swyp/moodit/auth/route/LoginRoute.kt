package com.swyp.moodit.auth.route

import androidx.compose.runtime.Composable
import com.swyp.moodit.auth.LoginScreen

@Composable
fun LoginRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToMain: () -> Unit
) {
    LoginScreen(
        onShowSnackbar = onShowSnackbar,
        onLoginClick = navigateToMain
    )
}