package com.swyp.moodit.tournament.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.navigation.BottomBarRoute
import com.swyp.moodit.navigation.TournamentRoute
import com.swyp.moodit.tournament.completedDetail.CompletedTournamentDetailRoute
import com.swyp.moodit.tournament.create.CreateTournamentRoute
import com.swyp.moodit.tournament.inProgressDetail.InProgressTournamentDetailRoute
import com.swyp.moodit.tournament.main.TournamentMainRoute
import com.swyp.moodit.tournament.matchUp.MatchUpRoute
import com.swyp.moodit.tournament.result.TournamentResultRoute

fun NavGraphBuilder.tournamentNavGraph(
    navController: NavController,
    onShowSnackbar: suspend (String, MooditSnackbarType?) -> Boolean,
    navigateToMissionDetail: (Long, MissionStatus) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToMatchUp: (Long, Boolean) -> Unit,
    navigateToHome: () -> Unit,
    navigateToTournamentMain: () -> Unit,
) {
    composable<BottomBarRoute.Tournament> {
        TournamentMainRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToInProgressTournamentDetail = { id ->
                navController.navigateToInProgressTournamentDetail(
                    tournamentId = id
                )
            },
            navigateToCompletedTournamentDetail = { id, userMissionId ->
                navController.navigateToCompletedTournamentDetail(
                    tournamentId = id,
                    userMissionId = userMissionId
                )
            },
            navigateToSetting = navigateToSetting
        )
    }

    composable<TournamentRoute.InProgressDetail>() {
        InProgressTournamentDetailRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToMatchUp = navigateToMatchUp,
            navigateToTournamentMain = navigateToTournamentMain
        )
    }

    composable<TournamentRoute.CompletedDetail>() {
        CompletedTournamentDetailRoute(onShowSnackbar = onShowSnackbar)
    }

    composable<TournamentRoute.CreateTournament>() {
        CreateTournamentRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToMatchUp = navigateToMatchUp
        )
    }

    composable<TournamentRoute.MatchUp>() {
        MatchUpRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToTournamentResult = { navController.navigateToTournamentResult(it) },
            navigateToHome = navigateToHome
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

fun NavController.navigateToInProgressTournamentDetail(tournamentId: Long) {
    navigate(TournamentRoute.InProgressDetail(tournamentId))
}

fun NavController.navigateToCompletedTournamentDetail(tournamentId: Long, userMissionId: Long) {
    navigate(TournamentRoute.CompletedDetail(tournamentId, userMissionId))
}

fun NavController.navigateToMatchUp(tournamentId: Long, isStarted: Boolean) {
    navigate(TournamentRoute.MatchUp(tournamentId, isStarted)) {
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