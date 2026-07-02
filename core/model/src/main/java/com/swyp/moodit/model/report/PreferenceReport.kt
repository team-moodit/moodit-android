package com.swyp.moodit.model.report

data class PreferenceReport(
    val totalSelectionCount: Long = 0L,
    val topPreference: PreferenceDetail = PreferenceDetail(),
    val distributions: List<PreferenceDetail> = emptyList()
)
