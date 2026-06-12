package com.swyp.moodit.round.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.round.route.RoundMainRoute

fun NavGraphBuilder.roundNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<BottomBarRoute.Round> {
        RoundMainRoute(onShowSnackbar = onShowSnackbar)
    }
}