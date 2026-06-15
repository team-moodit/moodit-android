package com.swyp.moodit.home.main

import com.swyp.moodit.navigation.MissionStatus
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class HomeMainContract {
    data class State(
        val isLoading: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        object NavigateToSetting : SideEffect
        object NavigateToCreateTournament : SideEffect
        data class NavigateToMissionDetail(val missionId: String, val status: MissionStatus) : SideEffect
        data class ShowSnackbar(val message: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        object OnSettingClick : Intent
        object OnCreateTournamentClick : Intent
        data class OnMissionClick(val missionId: String) : Intent
    }
}