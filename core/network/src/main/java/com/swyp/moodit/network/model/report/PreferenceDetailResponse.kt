package com.swyp.moodit.network.model.report

data class PreferenceDetailResponse(
    val type: String,
    val title: String,
    val selectedCount: Long,
    val percentage: Double
)
