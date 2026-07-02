package com.swyp.moodit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.swyp.moodit.auth.navigation.authNavGraph
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.home.navigation.homeNavGraph
import com.swyp.moodit.onboard.navigation.onBoardingNavGraph
import com.swyp.moodit.report.navigation.reportNavGraph
import com.swyp.moodit.tournament.navigation.tournamentNavGraph
import com.swyp.moodit.ui.MooditAppState

@Composable
fun MooditNavHost(
    modifier: Modifier = Modifier,
    appState: MooditAppState,
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = appState.startDestination
    ) {
        onBoardingNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar,
            navigateToLogin = { appState.navigateToLogin() }
        )

        authNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar,
            navigateToMain = { appState.navigateToMain() },
            navigateToInputNickname = { appState.navigateToInputNickname(it) },
            navigateToSetting = { appState.popBackStack() }
        )

        homeNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar,
            navigateToLogin = { appState.navigateToLogin() },
            navigateToHome = { appState.navigateToMain() },
            navigateToReport = { appState.navigateToReport() },
            navigateToCreateTournament = { appState.navigateToCreateTournament() },
            navigateToInputNickname = { appState.navigateToInputNickname(it) },
            navigateToMissionDetail = { missionId, status ->
                appState.navigateToMissionDetail(
                    missionId,
                    status
                )
            }
        )

        tournamentNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar,
            navigateToMissionDetail = { missionId, status ->
                appState.navigateToMissionDetail(
                    missionId,
                    status
                )
            },
            popBackStack = { appState.popBackStack() }
        )

        reportNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar
        )
    }
}