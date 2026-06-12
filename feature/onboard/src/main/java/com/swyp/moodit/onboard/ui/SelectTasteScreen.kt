package com.swyp.moodit.onboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import timber.log.Timber

@Composable
fun SelectTasteScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onNextClick: () -> Unit
) {
    Timber.i("SelectTasteScreen")
    Column {
        Text(text = "SelectTasteScreen")
        Button(onClick = onNextClick) {
            Text(text = "다음")
        }
    }
}