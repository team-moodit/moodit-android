package com.swyp.moodit.data.impl

import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.mapper.toModel
import com.swyp.moodit.data.repository.ReportRepository
import com.swyp.moodit.model.report.ReportSummary
import com.swyp.moodit.network.api.MooditApi
import com.swyp.moodit.network.model.getOrThrow
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val mooditApi: MooditApi
) : ReportRepository {
    override suspend fun getPreferenceReport(): Result<ReportSummary> {
        return try {
            val response = mooditApi.getReportSummary().getOrThrow()
            Result.Success(response.toModel())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}