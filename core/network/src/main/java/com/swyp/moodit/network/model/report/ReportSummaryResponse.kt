package com.swyp.moodit.network.model.report

data class ReportSummaryResponse(
    val summary: SummaryCountResponse,
    val preferenceReport: PreferenceReportResponse,
    val rateSummary: SatisfactionSummaryResponse
)
