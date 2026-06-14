package com.swyp.moodit.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface TournamentRoute: Route {
    @Serializable
    data object CreateTournament: TournamentRoute

    @Serializable
    data class MatchUp(val photoId: String)
}