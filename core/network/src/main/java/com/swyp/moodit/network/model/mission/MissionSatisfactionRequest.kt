package com.swyp.moodit.network.model.mission

data class MissionSatisfactionRequest(
    val userMissionId: Long,
    val rate: Float,
    val content: String
)
