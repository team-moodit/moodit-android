package com.swyp.moodit.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface TournamentRoute : Route {
    @Serializable
    data object CreateTournament : TournamentRoute

    @Serializable
    data class MatchUp(val tournamentId: Long, val isStarted: Boolean)

    @Serializable
    data class Result(val matchResultId: Long) : TournamentRoute

    @Serializable
    data class InProgressDetail(val tournamentId: Long) : TournamentRoute

    @Serializable
    data class CompletedDetail(val tournamentId: Long, val userMissionId: Long) : TournamentRoute
}