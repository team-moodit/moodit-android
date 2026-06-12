package com.swyp.moodit.report

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ReportMainScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    Text(text = "ReportMainScreen")
}