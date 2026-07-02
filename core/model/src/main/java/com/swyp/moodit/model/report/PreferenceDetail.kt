package com.swyp.moodit.model.report

data class PreferenceDetail(
    val type: String = "",
    val title: String = "",
    val selectedCount: Long = 0L,
    val percentage: Double = 0.0
)
