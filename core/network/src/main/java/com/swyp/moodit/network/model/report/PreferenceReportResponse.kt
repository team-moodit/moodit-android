package com.swyp.moodit.network.model.report

import com.swyp.moodit.model.report.Distribution

data class PreferenceReportResponse(
    val totalMatchCount: Long,
    val resultType: String,
    val topPreference: PreferenceDetailResponse? = null,
    val topPreferenceDetail: TopPreferenceDetailResponse? = null,
    val distributions: List<DistributionResponse>
)
