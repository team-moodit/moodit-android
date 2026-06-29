package com.swyp.moodit.tournament.result

import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class TournamentResultContract {
    data class State(
        val isLoading: Boolean = false,
        val missionId: Long = 0L,
        val missions: List<Mission> = emptyList(),
        val selectedMission: Long? = null
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data class NavigateToMissionDetail(val missionId: Long, val status: MissionStatus) :
            SideEffect
    }

    sealed interface Intent : UiIntent {
        data class OnMissionSelect(val missionId: Long) : Intent
        data object OnMissionDetailClick : Intent
    }
}