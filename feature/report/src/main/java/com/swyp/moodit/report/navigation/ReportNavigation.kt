package com.swyp.moodit.report.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.report.main.ReportMainRoute

fun NavGraphBuilder.reportNavGraph(
    navController: NavController,
    navigateToSetting: () -> Unit,
    navigateToMissionDetail: (Long) -> Unit,
    navigateToHome: () -> Unit,
    navigateToCreateMoodMatch: () -> Unit,
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean
) {
    composable<BottomBarRoute.Report>() {
        ReportMainRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToSetting = navigateToSetting,
            navigateToMissionDetail = navigateToMissionDetail,
            navigateToHome = navigateToHome,
            navigateToCreateMoodMatch = navigateToCreateMoodMatch
        )
    }
}

fun NavController.navigateToReport(navOptions: NavOptions) {
    navigate(BottomBarRoute.Report, navOptions)
}