package com.swyp.moodit.home.missionDetail

import com.swyp.moodit.navigation.MissionStatus
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class MissionDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val isCompleted: Boolean = true,
        val status: MissionStatus = MissionStatus.DEFAULT
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data object NavigateToReportReady : SideEffect
        data object NavigateToHome : SideEffect
    }

    sealed interface Intent : UiIntent {
        data object OnCompleteClick : Intent
    }
}