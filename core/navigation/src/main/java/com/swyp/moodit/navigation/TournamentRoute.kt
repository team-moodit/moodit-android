package com.swyp.moodit.navigation

import com.swyp.moodit.model.tournament.InProgressMatchState
import kotlinx.serialization.Serializable

@Serializable
sealed interface TournamentRoute : Route {
    @Serializable
    data object CreateTournament : TournamentRoute

    @Serializable
    data class MatchUp(val tournamentId: Long, val isStarted: Boolean)

    @Serializable
    data class Result(val matchId: Long) : TournamentRoute

    @Serializable
    data class InProgressDetail(val tournamentId: Long, val matchResultId: Long, val matchState: InProgressMatchState) : TournamentRoute

    @Serializable
    data class CompletedDetail(val tournamentId: Long, val userMissionId: Long) : TournamentRoute
}