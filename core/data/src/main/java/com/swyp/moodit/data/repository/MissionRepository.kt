package com.swyp.moodit.data.repository

import com.swyp.moodit.common.util.Result
import com.swyp.moodit.model.Mission

interface MissionRepository {
    suspend fun getMissionDetail(userMissionId: Long): Result<Mission>
    suspend fun completeMission(userMissionId: Long): Result<Long>
    suspend fun submitSatisfaction(userMissionId: Long, satisfactionScore: Float, feedbackOptions: List<String>): Result<Unit>
}