package com.swyp.moodit.network.model.mission

import com.swyp.moodit.network.model.tournament.MissionMatchResultResponse

data class MissionDetailResponse(
    val userMissionId: Long,
    val missionTitle: String,
    val missionState: String,
    val missionCompletedAt: String? = null,
    val matchResult: MissionMatchResultResponse,
    val satisfactionScore: Float = 0.0f
)
