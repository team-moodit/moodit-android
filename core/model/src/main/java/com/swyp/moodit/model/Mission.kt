package com.swyp.moodit.model

data class Mission(
    val userMissionId: Long = 0L,
    val title: String = "",
    val missionState: MissionState = MissionState.IN_PROGRESS,
    val imageUrl: String = "",
    val roundCount: Int = 0,
    val matchCompletedAt: String = ""
)

enum class MissionState {
    IN_PROGRESS,
    COMPLETED
}

