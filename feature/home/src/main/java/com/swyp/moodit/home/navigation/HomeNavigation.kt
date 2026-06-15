package com.swyp.moodit.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.swyp.moodit.home.main.HomeMainRoute
import com.swyp.moodit.home.missionDetail.MissionDetailRoute
import com.swyp.moodit.home.reportReady.ReportReadyRoute
import com.swyp.moodit.home.setting.SettingRoute
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.navigation.HomeRoute
import com.swyp.moodit.navigation.MissionStatus

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToReport: () -> Unit,
    navigateToCreateTournament: () -> Unit,
    navigateToMissionDetail: (String, MissionStatus) -> Unit
) {
    composable<BottomBarRoute.Home>() {
        HomeMainRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToSetting = { navController.navigateToSetting() },
            navigateToCreateTournament = navigateToCreateTournament,
            navigateToMissionDetail = navigateToMissionDetail
        )
    }

    composable<HomeRoute.Setting>() {
        SettingRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToLogin = navigateToLogin
        )
    }

    composable<HomeRoute.MissionDetail>() {
        MissionDetailRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToReportReady = { navController.navigateToReportReady() },
            navigateToHome = navigateToHome
        )
    }

    composable<HomeRoute.ReportReady>() {
        ReportReadyRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToReport = navigateToReport,
            navigateToHome = navigateToHome
        )
    }
}

fun NavController.navigateToHome(navOptions: NavOptions) {
    navigate(BottomBarRoute.Home, navOptions)
}

fun NavController.navigateToSetting() {
    navigate(HomeRoute.Setting)
}

fun NavController.navigateToReportReady() {
    navigate(HomeRoute.ReportReady)
}