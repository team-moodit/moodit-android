package com.swyp.moodit.network.model.report

data class TopPreferenceDetailResponse(
    val detailType: String? = null,
    val title: String,
    val insightTitle: String,
    val selectedCount: Int,
    val percentage: Double
)
