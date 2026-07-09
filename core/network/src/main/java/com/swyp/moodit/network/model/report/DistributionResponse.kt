package com.swyp.moodit.network.model.report

data class DistributionResponse(
    val type: String,
    val title: String,
    val selectedCount: Int,
    val percentage: Double
)
