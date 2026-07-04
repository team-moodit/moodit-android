package com.swyp.moodit.network.model.report

data class PreferenceReportResponse(
    val totalMatchCount: Long,
    val topPreference: PreferenceDetailResponse,
    val distributions: List<PreferenceDetailResponse>
)
