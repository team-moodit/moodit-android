package com.swyp.moodit.network.model.report

data class SatisfactionSummaryResponse(
    val count: Long,
    val rate: Double,
    val minRate: Double,
    val maxRate: Double
)
