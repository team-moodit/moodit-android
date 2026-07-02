package com.swyp.moodit.model.report

data class ReportSummary(
    val summary: SummaryCount = SummaryCount(),
    val preferenceReport: PreferenceReport = PreferenceReport(),
    val rateSummary: SatisfactionSummary = SatisfactionSummary()
)