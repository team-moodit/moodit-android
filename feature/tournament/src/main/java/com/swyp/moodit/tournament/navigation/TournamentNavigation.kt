package com.swyp.moodit.tournament.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.navigation.MissionStatus
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.tournament.create.CreateTournamentRoute
import com.swyp.moodit.tournament.detail.TournamentDetailRoute
import com.swyp.moodit.tournament.main.TournamentMainRoute
import com.swyp.moodit.tournament.matchUp.MatchUpRoute
import com.swyp.moodit.tournament.result.TournamentResultRoute

fun NavGraphBuilder.tournamentNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToMissionDetail: (Long, MissionStatus) -> Unit,
    popBackStack: () -> Unit,
) {
    composable<BottomBarRoute.Tournament> {
        TournamentMainRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToTournamentDetail = { navController.navigateToTournamentDetail(it) }
        )
    }

    composable<TournamentRoute.Detail>() {
        TournamentDetailRoute(onShowSnackbar = onShowSnackbar)
    }

    composable<TournamentRoute.CreateTournament>() {
        CreateTournamentRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToMatchUp = { navController.navigateToMatchUp(it) }
        )
    }

    composable<TournamentRoute.MatchUp>() {
        MatchUpRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToTournamentResult = { navController.navigateToTournamentResult(it) },
            navigateToHome = popBackStack
        )
    }

    composable<TournamentRoute.Result>() {
        TournamentResultRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToMissionDetail = navigateToMissionDetail
        )
    }
}

fun NavController.navigateToTournament(navOptions: NavOptions) {
    navigate(BottomBarRoute.Tournament, navOptions)
}

fun NavController.navigateToTournamentDetail(tournamentId: String) {
    navigate(TournamentRoute.Detail(tournamentId))
}

fun NavController.navigateToMatchUp(tournamentId: Long) {
    navigate(TournamentRoute.MatchUp(tournamentId)) {
        popUpTo(BottomBarRoute.Home) {
            inclusive = false
        }
    }
}

fun NavController.navigateToTournamentResult(winnerCandidateId: Long) {
    navigate(TournamentRoute.Result(winnerCandidateId)) {
        popUpTo(BottomBarRoute.Home) {
            inclusive = false
        }
    }
}