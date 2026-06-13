package com.swyp.moodit.onboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.swyp.moodit.navigation.OnBoardingRoute
import com.swyp.moodit.onboard.report.ReportRoute
import com.swyp.moodit.onboard.saveTaste.SaveTasteRoute
import com.swyp.moodit.onboard.selectTaste.SelectTasteRoute

fun NavGraphBuilder.onBoardingNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToLogin: () -> Unit,
) {
    composable<OnBoardingRoute.SaveTaste>() {
        SaveTasteRoute(
            navigateToSelectTaste = { navController.navigateToSelectTaste() }
        )
    }

    composable<OnBoardingRoute.SelectTaste>() {
        SelectTasteRoute(
            navigateToReport = { navController.navigateToReport() })
    }

    composable<OnBoardingRoute.Report>() {
        ReportRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToLogin = navigateToLogin
        )
    }
}

fun NavController.navigateToSelectTaste() {
    navigate(OnBoardingRoute.SelectTaste)
}

fun NavController.navigateToReport() {
    navigate(OnBoardingRoute.Report)
}