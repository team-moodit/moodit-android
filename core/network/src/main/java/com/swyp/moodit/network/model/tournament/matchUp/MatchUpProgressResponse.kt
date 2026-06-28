package com.swyp.moodit.network.model.tournament.matchUp

data class MatchUpProgressResponse(
    val tournamentTitle: String,
    val roundTitle: String,
    val currentMatchIndex: Int,
    val totalMatchUpInRound: Int,
    val isTournamentCompleted: Boolean,
    val nextMatchUp: MatchUpResponse?,
    val reasons: List<MatchUpReasonResponse>
)
