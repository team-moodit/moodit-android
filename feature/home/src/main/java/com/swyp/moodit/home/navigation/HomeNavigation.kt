package com.swyp.moodit.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.swyp.moodit.home.createRound.CreateRoundRoute
import com.swyp.moodit.home.main.HomeMainRoute
import com.swyp.moodit.home.setting.SettingRoute
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.navigation.HomeRoute

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToLogin: () -> Unit
) {
    composable<BottomBarRoute.Home>() {
        HomeMainRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToSetting = { navController.navigateToSetting() },
            navigateToCreateRound = { navController.navigateToCreateRound() }
        )
    }

    composable<HomeRoute.Setting>() {
        SettingRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToLogin = navigateToLogin
        )
    }

    composable<HomeRoute.CreateRound>() {
        CreateRoundRoute(onShowSnackbar = onShowSnackbar)
    }
}

fun NavController.navigateToHome(navOptions: NavOptions) {
    navigate(BottomBarRoute.Home, navOptions)
}

fun NavController.navigateToSetting() {
    navigate(HomeRoute.Setting)
}

fun NavController.navigateToCreateRound() {
    navigate(HomeRoute.CreateRound)
}