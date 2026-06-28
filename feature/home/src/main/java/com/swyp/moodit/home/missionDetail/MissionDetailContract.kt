package com.swyp.moodit.home.missionDetail

import com.swyp.moodit.model.FeedbackOption
import com.swyp.moodit.model.Mission
import com.swyp.moodit.navigation.MissionStatus
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class MissionDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val status: MissionStatus = MissionStatus.DEFAULT,
        val missionInfo: Mission = Mission(),
        val selectedFeedback: FeedbackOption? = null
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data object NavigateToReportReady : SideEffect
        data object NavigateToHome : SideEffect
    }

    sealed interface Intent : UiIntent {
        data object OnCompleteClick : Intent
        object LoadMissionDetail : Intent
    }
}