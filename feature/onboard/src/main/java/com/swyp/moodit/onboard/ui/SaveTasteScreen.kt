package com.swyp.moodit.onboard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun SaveTasteScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onNextClick: () -> Unit
) {
    Timber.i("SaveTasteScreen")
    Column {
        Text(text = "SaveTasteScreen")
        Button(onClick = onNextClick) {
            Text(text = "다음")
        }
    }
}