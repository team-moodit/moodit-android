package com.swyp.moodit.data.repository

import com.swyp.moodit.common.util.Result
import com.swyp.moodit.model.report.ReportSummary

interface ReportRepository {
    suspend fun getPreferenceReport(): Result<ReportSummary>
}