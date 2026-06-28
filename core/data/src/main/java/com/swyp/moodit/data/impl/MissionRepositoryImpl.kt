package com.swyp.moodit.data.impl

import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.mapper.toModel
import com.swyp.moodit.data.repository.MissionRepository
import com.swyp.moodit.model.Mission
import com.swyp.moodit.network.api.MooditApi
import com.swyp.moodit.network.model.getOrThrow
import javax.inject.Inject

internal class MissionRepositoryImpl @Inject constructor(
    private val mooditApi: MooditApi
) : MissionRepository {
    override suspend fun getMissionDetail(userMissionId: Long): Result<Mission> {
        return try {
            val response = mooditApi.getMissionDetail(userMissionId).getOrThrow()
            Result.Success(response.toModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}