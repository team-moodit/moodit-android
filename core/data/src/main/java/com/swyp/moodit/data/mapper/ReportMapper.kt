package com.swyp.moodit.data.mapper

import com.swyp.moodit.model.report.PreferenceDetail
import com.swyp.moodit.model.report.PreferenceReport
import com.swyp.moodit.model.report.ReportSummary
import com.swyp.moodit.model.report.SatisfactionSummary
import com.swyp.moodit.model.report.SummaryCount
import com.swyp.moodit.network.model.report.PreferenceDetailResponse
import com.swyp.moodit.network.model.report.PreferenceReportResponse
import com.swyp.moodit.network.model.report.ReportSummaryResponse
import com.swyp.moodit.network.model.report.SatisfactionSummaryResponse
import com.swyp.moodit.network.model.report.SummaryCountResponse

fun PreferenceDetailResponse.toModel(): PreferenceDetail {
    return PreferenceDetail(
        type = this.type,
        title = this.title,
        selectedCount = this.selectedCount,
        percentage = this.percentage
    )
}

fun SummaryCountResponse.toModel(): SummaryCount {
    return SummaryCount(
        totalMatchCount = this.totalMatchCount,
        reviewedMissionCount = this.reviewedMissionCount
    )
}

fun SatisfactionSummaryResponse.toModel(): SatisfactionSummary {
    return SatisfactionSummary(
        count = this.count,
        rate = this.rate,
        minRate = this.minRate,
        maxRate = this.maxRate
    )
}

fun PreferenceReportResponse.toModel(): PreferenceReport {
    return PreferenceReport(
        totalSelectionCount = this.totalMatchCount,
        topPreference = this.topPreference.toModel(),
        distributions = this.distributions.map { it.toModel() }
    )
}

fun ReportSummaryResponse.toModel(): ReportSummary {
    return ReportSummary(
        summary = this.summary.toModel(),
        preferenceReport = this.preferenceReport.toModel(),
        rateSummary = this.rateSummary.toModel()
    )
}