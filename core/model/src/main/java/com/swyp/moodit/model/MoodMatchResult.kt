package com.swyp.moodit.model

data class MoodMatchResult(
    val offerId: Long = 0L,
    val preferenceResultType: PreferenceResultType = PreferenceResultType.TIE,
    val missionSuggestions: List<MissionSuggestion> = emptyList(),
    val state: String = "",
    val assignedMissionId: Long = 0L,
    val matchResult: MissionMatchResult = MissionMatchResult()
)
