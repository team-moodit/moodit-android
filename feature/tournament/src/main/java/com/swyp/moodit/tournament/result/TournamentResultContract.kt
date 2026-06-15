package com.swyp.moodit.tournament.result

import com.swyp.moodit.navigation.MissionStatus
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class TournamentResultContract {
    data class State(
        val isLoading: Boolean = false,
        val missionId: String = ""
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data class NavigateToMissionDetail(val missionId: String, val status: MissionStatus): SideEffect
    }

    sealed interface Intent : UiIntent {
        data object OnMissionDetailClick : Intent
    }
}