package com.swyp.moodit.navigation

import com.swyp.moodit.model.tournament.TournamentState
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
    data class Detail(val tournamentId: Long, val tournamentType: TournamentState) : TournamentRoute
}