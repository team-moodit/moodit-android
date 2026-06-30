package com.swyp.moodit.network.model.tournament.matchUp

data class SaveMatchUpResponse(
    val nextMatchId: Long,
    val currentRound: Long,
    val currentRoundOrder: Long,
    val isTournamentFinished: Boolean
)
