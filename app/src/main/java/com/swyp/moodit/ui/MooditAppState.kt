package com.swyp.moodit.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.swyp.moodit.home.navigation.navigateToHome
import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.navigation.AuthRoute
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.navigation.HomeRoute
import com.swyp.moodit.navigation.MainBottomBarTab
import com.swyp.moodit.navigation.OnBoardingRoute
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.report.navigation.navigateToReport
import com.swyp.moodit.tournament.navigation.navigateToTournament

@Composable
fun rememberMooditAppState(
    navController: NavHostController = rememberNavController()
): MooditAppState {
    return remember(navController) {
        MooditAppState(navController = navController)
    }
}

class MooditAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = OnBoardingRoute.SaveTaste

    val currentTab: MainBottomBarTab?
        @Composable get() = MainBottomBarTab.entries.firstOrNull { navTab ->
            currentDestination?.hasRoute(navTab.route::class) == true
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
            MainBottomBarTab.TOURNAMENT -> navController.navigateToTournament(bottomTabNavOptions)
            MainBottomBarTab.REPORT -> navController.navigateToReport(bottomTabNavOptions)
        }
    }

    fun navigateToOnBoarding() {
        navController.navigate(route = startDestination) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToLogin() {
        navController.navigate(route = AuthRoute.Login) {
            popUpTo(navController.graph.id) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun navigateToInputNickname(isEditMode: Boolean) {
        navController.navigate(route = AuthRoute.InputNickname(isEditMode)) {
            if (!isEditMode) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
            launchSingleTop = true
        }
    }

    fun navigateToMain() {
        navController.navigate(BottomBarRoute.Home) {
            popUpTo(navController.graph.id) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun navigateToTournamentMain() {
        navController.navigate(BottomBarRoute.Tournament) {
            popUpTo(BottomBarRoute.Home) { inclusive = false }
            launchSingleTop = true
        }
    }

    fun navigateToReport() {
        navController.navigate(BottomBarRoute.Report) {
            popUpTo(BottomBarRoute.Home) { inclusive = false }
            launchSingleTop = true
        }
    }

    fun navigateToMatchUp(tournamentId: Long, isStarted: Boolean) {
        navController.navigate(TournamentRoute.MatchUp(tournamentId, isStarted))
    }

    fun navigateToSetting() {
        navController.navigate(HomeRoute.Setting)
    }

    fun navigateToCreateTournament() {
        navController.navigate(TournamentRoute.CreateTournament)
    }

    fun navigateToMissionDetail(missionId: Long, status: MissionStatus) {
        navController.navigate(HomeRoute.MissionDetail(missionId, status.name))
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    @Composable
    fun showBottomBar() =
        MainBottomBarTab.contains { currentDestination?.hasRoute(it::class) == true }
}