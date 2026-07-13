package com.swyp.moodit.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.home.main.HomeMainRoute
import com.swyp.moodit.home.missionDetail.MissionDetailRoute
import com.swyp.moodit.home.reportReady.ReportReadyRoute
import com.swyp.moodit.home.setting.SettingContract
import com.swyp.moodit.home.setting.SettingRoute
import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.navigation.HomeRoute

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToReport: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToCreateTournament: () -> Unit,
    navigateToInputNickname: (Boolean) -> Unit,
    navigateToMissionDetail: (Long, MissionStatus) -> Unit,
    navigateToMatchUp: (Long, Boolean) -> Unit,
    navigateToMatchResult: (Long) -> Unit
) {
    composable<BottomBarRoute.Home>() {
        HomeMainRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToSetting = navigateToSetting,
            navigateToCreateTournament = navigateToCreateTournament,
            navigateToMissionDetail = navigateToMissionDetail,
            navigateToMatchUp = navigateToMatchUp,
            navigateToMatchResult = navigateToMatchResult
        )
    }

    composable<HomeRoute.Setting>() {
        SettingRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToLogin = navigateToLogin,
            navigateToInputNickname = navigateToInputNickname
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