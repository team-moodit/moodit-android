package com.swyp.moodit.network.model.tournament

data class InProgressMatchResponse(
    val matchId: Long,
    val title: String,
    val currentRound: Int,
    val totalRound: Int,
    val lastPlayedAt: String
)

data class CompletedMatchResponse(
    val matchId: Long,
    val title: String,
    val winnerImageId: Long,
    val winnerImageUri: String,
    val completedAt: String
)
