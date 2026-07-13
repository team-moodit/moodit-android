package com.swyp.moodit.network.model.tournament

data class InProgressMatchResponse(
    val matchId: Long,
    val matchResultId: Long?,
    val matchState: String,
    val title: String,
    val currentRound: Int,
    val totalRound: Int,
    val lastPlayedAt: String,
    val currentMatchProgress: Int,
    val finalMatchProgress: Int
)

data class CompletedMatchResponse(
    val userMissionId: Long,
    val matchId: Long,
    val title: String,
    val winnerImageId: Long,
    val winnerImageUri: String,
    val completedAt: String
)
