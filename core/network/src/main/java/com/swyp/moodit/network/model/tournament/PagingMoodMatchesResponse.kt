package com.swyp.moodit.network.model.tournament

data class PagingInProgressMatchResponse(
    val content: List<InProgressMatchResponse>,
    val totalCount: Long,
    val hasNext: Boolean
)

data class PagingCompletedMatchResponse(
    val content: List<CompletedMatchResponse>,
    val totalCount: Int,
    val hasNext: Boolean
)

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
