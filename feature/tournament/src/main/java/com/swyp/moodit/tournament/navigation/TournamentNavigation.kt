package com.swyp.moodit.tournament.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.navigation.HomeRoute
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.tournament.create.CreateTournamentRoute
import com.swyp.moodit.tournament.main.TournamentMainRoute

fun NavGraphBuilder.tournamentNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<BottomBarRoute.Tournament> {
        TournamentMainRoute(onShowSnackbar = onShowSnackbar)
    }

    composable<TournamentRoute.CreateTournament>() {
        CreateTournamentRoute(onShowSnackbar = onShowSnackbar)
    }
}

fun NavController.navigateToTournament(navOptions: NavOptions) {
    navigate(BottomBarRoute.Tournament, navOptions)
}

fun NavController.navigateToCreateTournament() {
    navigate(TournamentRoute.CreateTournament)
}