package com.swyp.moodit.report.route

import androidx.compose.runtime.Composable
import com.swyp.moodit.report.ReportMainScreen

@Composable
fun ReportMainRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    ReportMainScreen(onShowSnackbar = onShowSnackbar)
}