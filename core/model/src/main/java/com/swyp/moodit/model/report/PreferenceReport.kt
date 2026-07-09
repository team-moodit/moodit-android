package com.swyp.moodit.model.report

data class PreferenceReport(
    val totalSelectionCount: Long = 0L,
    val resultType: ResultType = ResultType.NONE,
    val topPreference: PreferenceDetail = PreferenceDetail(),
    val topPreferenceDetail: TopPreferenceDetail = TopPreferenceDetail(),
    val distributions: List<Distribution> = emptyList()
)

enum class ResultType {
    NONE, PREFERENCE_TIE, PREFERENCE_ONLY, PREFERENCE_DETAIL
}
