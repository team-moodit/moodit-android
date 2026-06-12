package com.swyp.moodit.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.swyp.moodit.home.navigation.navigateToHome
import com.swyp.moodit.navigation.AuthRoute
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.navigation.MainBottomBarTab
import com.swyp.moodit.report.navigation.navigateToReport
import com.swyp.moodit.round.navigation.navigateToRound

class MooditAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = AuthRoute.Login

    val currentTab: MainBottomBarTab?
        @Composable get() = MainBottomBarTab.entries.firstOrNull { navTab ->
            currentDestination?.hasRoute(navTab::class) == true
        }

    fun navigateToMainBottomBarTab(navTab: MainBottomBarTab) {
        val bottomTabNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (navTab) {
            MainBottomBarTab.HOME -> navController.navigateToHome(bottomTabNavOptions)
            MainBottomBarTab.ROUND -> navController.navigateToRound(bottomTabNavOptions)
            MainBottomBarTab.REPORT -> navController.navigateToReport(bottomTabNavOptions)
        }
    }

    fun navigateToMain() {
        navController.navigate(BottomBarRoute.Home)
    }
}