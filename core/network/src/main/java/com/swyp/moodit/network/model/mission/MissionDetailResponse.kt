package com.swyp.moodit.network.model.mission

data class MissionDetailResponse(
    val userMissionId: Long,
    val missionTitle: String,
    val missionState: String,
    val matchRepresentativeImageUrl: String,
    val roundCount: Int,
    val matchCompletedAt: String
)
