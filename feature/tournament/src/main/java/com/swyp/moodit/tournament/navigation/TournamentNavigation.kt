package com.swyp.moodit.tournament.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.tournament.create.CreateTournamentRoute
import com.swyp.moodit.tournament.main.TournamentMainRoute
import com.swyp.moodit.tournament.matchUp.MatchUpRoute

fun NavGraphBuilder.tournamentNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<BottomBarRoute.Tournament> {
        TournamentMainRoute(onShowSnackbar = onShowSnackbar)
    }

    composable<TournamentRoute.CreateTournament>() {
        CreateTournamentRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToMatchUp = { navController.navigateToMatchUp() }
        )
    }

    composable<TournamentRoute.MatchUp>() {
        MatchUpRoute(onShowSnackbar = onShowSnackbar)
    }
}

fun NavController.navigateToTournament(navOptions: NavOptions) {
    navigate(BottomBarRoute.Tournament, navOptions)
}

fun NavController.navigateToMatchUp() {
    navigate(TournamentRoute.MatchUp)
}