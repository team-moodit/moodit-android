package com.swyp.moodit.onboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import timber.log.Timber

@Composable
fun ReportScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onStartClick: () -> Unit
) {
    Timber.i("ReportScreen")
    Column {
        Text(text = "ReportScreen")
        Button(onClick = onStartClick) {
            Text(text = "시작하기")
        }
    }
}