package com.swyp.moodit.data.mapper

import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionState
import com.swyp.moodit.network.model.mission.MissionCompleteResponse
import com.swyp.moodit.network.model.mission.MissionDetailResponse

fun MissionDetailResponse.toModel(): Mission {
    val missionState = MissionState.entries.find { it.name == this.missionState }
        ?: MissionState.IN_PROGRESS

    return Mission(
        userMissionId = this.userMissionId,
        missionTitle = this.missionTitle,
        missionState = missionState,
        imageUrl = this.matchRepresentativeImageUrl,
        roundCount = this.matchRoundCount,
        matchCompletedAt = this.matchCompletedAt,
        missionCompletedAt = this.missionCompletedAt ?: "",
        matchTitle = this.matchTitle,
        matchPreferenceType = this.matchPreferenceType ?: "",
        satisfactionScore = this.satisfactionScore
    )
}

fun MissionCompleteResponse.toModel(): Mission {
    val missionState = MissionState.entries.find { it.name == this.missionState }
        ?: MissionState.IN_PROGRESS

    return Mission(
        userMissionId = this.userMissionId,
        missionTitle = this.missionTitle,
        missionState = missionState,
        imageUrl = this.matchRepresentativeImageUrl,
        roundCount = this.matchRoundCount,
        matchCompletedAt = this.matchCompletedAt,
        missionCompletedAt = this.missionCompletedAt ?: "",
        matchTitle = this.matchTitle,
        matchPreferenceType = this.matchPreferenceType ?: "",
        satisfactionScore = this.satisfactionScore
    )
}