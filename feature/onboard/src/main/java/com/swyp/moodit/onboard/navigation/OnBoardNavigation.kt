package com.swyp.moodit.onboard.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.swyp.moodit.navigation.OnBoardingRoute
import com.swyp.moodit.onboard.route.ReportRoute
import com.swyp.moodit.onboard.route.SaveTasteRoute
import com.swyp.moodit.onboard.route.SelectTasteRoute

fun NavGraphBuilder.onBoardingNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToLogin: () -> Unit,
) {
    composable<OnBoardingRoute.SaveTaste>() {
        SaveTasteRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToSelectTaste = { navController.navigateToSelectTaste() }
        )
    }

    composable<OnBoardingRoute.SelectTaste>() {
        SelectTasteRoute(
            onShowSnackbar = onShowSnackbar,
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