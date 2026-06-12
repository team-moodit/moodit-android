package com.swyp.moodit.round

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RoundMainScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    Text(text = "RoundMainScreen")
}