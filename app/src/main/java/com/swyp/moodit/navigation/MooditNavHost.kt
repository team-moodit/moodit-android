package com.swyp.moodit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.swyp.moodit.auth.navigation.authNavGraph
import com.swyp.moodit.home.navigation.homeNavGraph
import com.swyp.moodit.report.navigation.reportNavGraph
import com.swyp.moodit.round.navigation.roundNavGraph
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
        authNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar
        )

        homeNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar
        )

        roundNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar
        )

        reportNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar
        )
    }
}