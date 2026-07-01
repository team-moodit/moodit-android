package com.swyp.moodit.network.model.tournament

data class MissionOfferResponse(
    val offerId: Long,
    val preferenceResultType: String,
    val items: List<MissionSuggestionResponse>,
    val state: String,
    val assignedMissionId: Long? = null,
    val matchResult: MissionMatchResultResponse
)

data class MissionMatchResultResponse(
    val matchResultId: Long,
    val matchTitle: String,
    val matchRepresentativeImageUrl: String,
    val matchPreferenceTypeTitle: String,
    val matchRoundCount: Int,
    val matchCompletedAt: String
)

data class MissionSuggestionResponse(
    val id: Long,
    val title: String
)
