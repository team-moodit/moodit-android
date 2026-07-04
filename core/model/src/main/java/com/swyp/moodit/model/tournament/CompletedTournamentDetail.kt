package com.swyp.moodit.model.tournament

import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionMatchResult

data class CompletedTournamentDetail(
    val imageUris: List<String>,
    val missionInfo: Mission
)
