package com.swyp.moodit.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onLoginClick: () -> Unit
) {
    Column {
        Text(text = "LoginScreen")
        Button(onClick = onLoginClick) {
            Text(text = "로그인")
        }
    }
}