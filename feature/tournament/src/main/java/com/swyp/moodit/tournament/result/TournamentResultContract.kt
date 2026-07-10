package com.swyp.moodit.tournament.result

import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.model.MissionSuggestion
import com.swyp.moodit.model.MoodMatchResult
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class TournamentResultContract {
    data class State(
        val isLoading: Boolean = false,
        val nickname: String = "",
        val moodMatchResult: MoodMatchResult = MoodMatchResult(),
        val selectedMission: MissionSuggestion? = null,
        val userMissionId: Long = 0L
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data class NavigateToMissionDetail(val missionId: Long, val status: MissionStatus) :
            SideEffect
        data object NavigateToHome : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class OnMissionSelect(val mission: MissionSuggestion) : Intent
        data object OnMissionDetailClick : Intent
        data object LoadResult : Intent
        data object OnExitClick : Intent
    }
}