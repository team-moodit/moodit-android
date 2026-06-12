package com.swyp.moodit.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import timber.log.Timber

@Composable
fun HomeMainScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    Timber.d("히득히득")
    Text(text = "HomeMainScreen")
}