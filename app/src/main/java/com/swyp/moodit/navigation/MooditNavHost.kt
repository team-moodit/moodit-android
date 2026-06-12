package com.swyp.moodit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.swyp.moodit.auth.navigation.authNavGraph
import com.swyp.moodit.home.navigation.homeNavGraph
import com.swyp.moodit.report.navigation.reportNavGraph
import com.swyp.moodit.round.navigation.roundNavGraph

@Composable
fun MooditNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    NavHost(
        navController = navController,
        startDestination = AuthRoute.Login
    ) {
        authNavGraph(onShowSnackbar = onShowSnackbar)

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