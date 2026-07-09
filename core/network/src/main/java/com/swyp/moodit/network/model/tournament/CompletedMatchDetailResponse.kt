package com.swyp.moodit.network.model.tournament

data class CompletedMatchDetailResponse(
    val title: String,
    val winnerImage: MatchImageResponse,
    val preferenceResult: PreferenceResultResponse,
    val completedAt: String,
    val selectedImages: List<MatchImageResponse>,
    val preferenceTitle: String?
)

data class PreferenceResultResponse(
    val preferenceType: String?,
    val preferenceDetailType: String?
)

