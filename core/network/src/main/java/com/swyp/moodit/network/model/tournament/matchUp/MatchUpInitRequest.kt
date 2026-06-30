package com.swyp.moodit.network.model.tournament.matchUp

data class MatchUpInitRequest(
    val matchId: Long
) {
    override fun toString(): String {
        return matchId.toString()
    }
}
