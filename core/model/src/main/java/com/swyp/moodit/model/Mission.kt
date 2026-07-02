package com.swyp.moodit.model

data class Mission(
    val userMissionId: Long = 0L,
    val missionTitle: String = "",
    val missionState: MissionState = MissionState.IN_PROGRESS,
    val missionCompletedAt: String = "",
    val matchResult: MissionMatchResult = MissionMatchResult(),
    val satisfactionScore: Float = 0.0f
)

enum class MissionState {
    IN_PROGRESS,
    COMPLETED,
    REVIEWED
}
