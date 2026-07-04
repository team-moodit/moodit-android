package com.swyp.moodit.network.model.report

data class PreferenceReportResponse(
    val totalSelectionCount: Long,
    val topPreference: PreferenceDetailResponse,
    val distributions: List<PreferenceDetailResponse>
)
