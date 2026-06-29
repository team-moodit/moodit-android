package com.swyp.moodit.network.model.mission

data class MissionSatisfactionRequest(
    val satisfactionScore: Float,
    val dissatisfactionReasons: List<String>
)
