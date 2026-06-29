package com.swyp.moodit.model

data class Mission(
    val userMissionId: Long = 0L,
    val missionTitle: String = "",
    val missionState: MissionState = MissionState.IN_PROGRESS,
    val imageUrl: String = "",
    val roundCount: Int = 0,
    val missionCompletedAt: String = "",
    val matchCompletedAt: String = "",
    val matchTitle: String = "",
    val matchPreferenceType: String = "",
    val satisfactionScore: Float = 0.0f
)

enum class MissionState {
    IN_PROGRESS,
    COMPLETED
}

