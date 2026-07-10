package com.swyp.moodit.network.model.tournament

data class InProgressMatchDetailResponse(
    val tournamentTitle: String,
    val totalRounds: Int,
    val currentRound: Int,
    val currentMatchOrder: Int,
    val matchInfo: InProgressMatchInfoResponse,
    val selectedImages: List<MatchImageResponse>
)

data class InProgressMatchInfoResponse(
    val totalImageCount: Int,
    val LastPlayedAt: String
)

data class MatchImageResponse(
    val id: Long,
    val photoUri: String
)
