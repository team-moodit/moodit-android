package com.swyp.moodit.network.model.report

data class TopPreferenceDetailResponse(
    val detailType: String? = null,
    val title: String,
    val selectedCount: Int,
    val percentage: Double
)
