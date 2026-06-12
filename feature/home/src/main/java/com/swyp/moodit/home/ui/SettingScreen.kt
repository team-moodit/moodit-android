package com.swyp.moodit.home.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import timber.log.Timber

@Composable
fun SettingScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    Text(text = "SettingScreen")
}