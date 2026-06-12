package com.swyp.moodit.report.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.report.route.ReportMainRoute

fun NavGraphBuilder.reportNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<BottomBarRoute.Report>() {
        ReportMainRoute(onShowSnackbar = onShowSnackbar)
    }
}