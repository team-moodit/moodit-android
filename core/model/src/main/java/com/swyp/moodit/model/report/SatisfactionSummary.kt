package com.swyp.moodit.model.report

data class SatisfactionSummary(
    val count: Long = 0L,
    val rate: Double = 0.0,
    val minRate: Double = 0.0,
    val maxRate: Double = 0.0
)