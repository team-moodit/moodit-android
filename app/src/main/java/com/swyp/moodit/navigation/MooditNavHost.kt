package com.swyp.moodit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.swyp.moodit.auth.navigation.authNavGraph
import com.swyp.moodit.auth.navigation.navigateToLogin
import com.swyp.moodit.home.navigation.homeNavGraph
import com.swyp.moodit.onboard.navigation.onBoardingNavGraph
import com.swyp.moodit.report.navigation.reportNavGraph
import com.swyp.moodit.tournament.navigation.tournamentNavGraph
import com.swyp.moodit.ui.MooditAppState

@Composable
fun MooditNavHost(
    modifier: Modifier = Modifier,
    appState: MooditAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = appState.startDestination
    ) {
        onBoardingNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar,
            navigateToLogin = { navController.navigateToLogin() }
        )

        authNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar,
            navigateToMain = { appState.navigateToMain() }
        )

        homeNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar,
            navigateToLogin = { navController.navigateToLogin() },
            navigateToHome = { appState.navigateToMain() },
            navigateToReport = { appState.navigateToReport() },
            navigateToCreateTournament = { appState.navigateToCreateTournament() },
            navigateToMissionDetail = { missionId, status -> appState.navigateToMissionDetail(missionId, status) }
        )

        tournamentNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar,
            navigateToMissionDetail = { missionId, status -> appState.navigateToMissionDetail(missionId, status) }
        )

        reportNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar
        )
    }
}