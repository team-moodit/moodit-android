package com.swyp.moodit.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    Text(text = "LoginScreen")
}