package com.swyp.moodit.model.report

data class Distribution(
    val type: String = "",
    val detailType: String = "",
    val title : String = "",
    val selectedCount: Int = 0,
    val percentage: Double = 0.0
)
