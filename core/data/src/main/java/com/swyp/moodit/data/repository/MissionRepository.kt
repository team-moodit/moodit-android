package com.swyp.moodit.data.repository

import com.swyp.moodit.common.util.Result
import com.swyp.moodit.model.Mission

interface MissionRepository {
    suspend fun getMissionDetail(userMissionId: Long): Result<Mission>
}