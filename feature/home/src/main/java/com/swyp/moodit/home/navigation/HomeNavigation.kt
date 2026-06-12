package com.swyp.moodit.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.swyp.moodit.home.route.HomeMainRoute
import com.swyp.moodit.home.route.SettingRoute
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.navigation.HomeRoute

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<BottomBarRoute.Home>() {
        HomeMainRoute(onShowSnackbar = onShowSnackbar,
            navigateToSetting = {
                navController.navigateToSetting()
            })
    }

    composable<HomeRoute.Setting>() {
        SettingRoute(onShowSnackbar = onShowSnackbar)
    }
}

fun NavController.navigateToHome(navOptions: NavOptions) {
    navigate(BottomBarRoute.Home, navOptions)
}

fun NavController.navigateToSetting() {
    navigate(HomeRoute.Setting)
}