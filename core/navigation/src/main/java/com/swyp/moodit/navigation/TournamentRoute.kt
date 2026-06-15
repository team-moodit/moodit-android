package com.swyp.moodit.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface TournamentRoute : Route {
    @Serializable
    data object CreateTournament : TournamentRoute

    @Serializable
    data object MatchUp

    @Serializable
    data class Result(val winnerCandidateId: Long) : TournamentRoute

    @Serializable
    data class Detail(val tournamentId: String) : TournamentRoute
}