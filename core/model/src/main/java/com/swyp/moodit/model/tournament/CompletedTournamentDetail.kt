package com.swyp.moodit.model.tournament

import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionMatchResult

data class CompletedTournamentDetail(
    val id: Long = 0L,
    val imageUris: List<String> = emptyList(),
    val missionInfo: Mission = Mission()
)
