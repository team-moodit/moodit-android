package com.swyp.moodit.network.model.tournament.matchUp

data class MatchUpInitResponse(
    val tournamentTitle: String,
    val totalRounds: Int,
    val currentRound: Int,
    val roundName: String,
    val isTournamentCompleted: Boolean,
    val nextMatchUp: MatchUpResponse?,
    val reasons: List<MatchUpReasonResponse>
)
