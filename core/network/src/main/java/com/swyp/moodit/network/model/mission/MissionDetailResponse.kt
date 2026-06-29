package com.swyp.moodit.network.model.mission

data class MissionDetailResponse(
    val userMissionId: Long,
    val missionTitle: String,
    val missionState: String,
    val matchRepresentativeImageUrl: String,
    val matchRoundCount: Int,
    val missionCompletedAt: String? = null,
    val matchCompletedAt: String,
    val matchTitle: String,
    val matchPreferenceType: String? = null,
    val satisfactionScore: Float = 0.0f
)
