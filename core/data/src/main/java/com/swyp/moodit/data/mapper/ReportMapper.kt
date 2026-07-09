package com.swyp.moodit.data.mapper

import com.swyp.moodit.model.report.Distribution
import com.swyp.moodit.model.report.PreferenceDetail
import com.swyp.moodit.model.report.PreferenceReport
import com.swyp.moodit.model.report.ReportSummary
import com.swyp.moodit.model.report.ResultType
import com.swyp.moodit.model.report.SatisfactionSummary
import com.swyp.moodit.model.report.SummaryCount
import com.swyp.moodit.model.report.TopPreferenceDetail
import com.swyp.moodit.network.model.report.DistributionResponse
import com.swyp.moodit.network.model.report.PreferenceDetailResponse
import com.swyp.moodit.network.model.report.PreferenceReportResponse
import com.swyp.moodit.network.model.report.ReportSummaryResponse
import com.swyp.moodit.network.model.report.SatisfactionSummaryResponse
import com.swyp.moodit.network.model.report.SummaryCountResponse
import com.swyp.moodit.network.model.report.TopPreferenceDetailResponse

fun PreferenceDetailResponse.toModel(): PreferenceDetail {
    return PreferenceDetail(
        type = this.type,
        title = this.title,
        selectedCount = this.selectedCount,
        percentage = this.percentage
    )
}

fun TopPreferenceDetailResponse.toModel(): TopPreferenceDetail {
    return TopPreferenceDetail(
        detailType = this.detailType ?: "",
        title = this.title,
        selectedCount = this.selectedCount,
        percentage = this.percentage
    )
}

fun SummaryCountResponse.toModel(): SummaryCount {
    return SummaryCount(
        totalMatchCount = this.totalMatchCount,
        reviewedMissionCount = this.completedMissionCount
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
        resultType = this.resultType.toResultTypeModel(),
        topPreference = this.topPreference?.toModel() ?: PreferenceDetail(),
        topPreferenceDetail = this.topPreferenceDetail?.toModel() ?: TopPreferenceDetail(),
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

fun DistributionResponse.toModel(): Distribution {
    return Distribution(
        type = this.type,
        title = this.title,
        selectedCount = this.selectedCount,
        percentage = this.percentage
    )
}

fun String.toResultTypeModel(): ResultType {
    return ResultType.entries.find { it.name == this } ?: ResultType.NONE
}