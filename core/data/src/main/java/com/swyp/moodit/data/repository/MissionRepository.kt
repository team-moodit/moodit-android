package com.swyp.moodit.data.repository

import com.swyp.moodit.common.util.Result
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MoodMatchResult

interface MissionRepository {
    suspend fun getMissionDetail(userMissionId: Long): Result<Mission>
    suspend fun completeMission(userMissionId: Long): Result<Mission>
    suspend fun submitSatisfaction(userMissionId: Long, satisfactionScore: Float, feedbackOptions: List<String>): Result<Unit>
    suspend fun deleteMission(userMissionId: Long): Result<Unit>
    suspend fun getMissionOffers(matchResultId: Long): Result<MoodMatchResult>
    suspend fun acceptMissionOffer(offerId: Long, candidateId: Long): Result<Long>
}