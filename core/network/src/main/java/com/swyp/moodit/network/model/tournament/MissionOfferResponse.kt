package com.swyp.moodit.network.model.tournament

data class MissionOfferResponse(
    val offerId: Long,
    val preferenceResultType: String,
    val items: List<MissionSuggestionResponse>,
    val state: String,
    val assignedMissionId: Long? = null
)

data class MissionSuggestionResponse(
    val id: Long,
    val title: String
)
