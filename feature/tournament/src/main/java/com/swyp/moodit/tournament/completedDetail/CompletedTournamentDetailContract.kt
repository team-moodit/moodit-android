package com.swyp.moodit.tournament.completedDetail

import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionMatchResult
import com.swyp.moodit.model.tournament.CompletedTournamentDetail
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class CompletedTournamentDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val nickname: String = "",
        val tournamentId: Long = 0L,
        val userMissionId: Long = 0L,
        val tournamentDetail: CompletedTournamentDetail = CompletedTournamentDetail(),
        val mission: Mission = Mission(),
        val selectedTab: CompletedTournamentTab = CompletedTournamentTab.MOOD_MATCH
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class SelectTab(val tab: CompletedTournamentTab) : Intent
        data object LoadMissionInfo : Intent
    }
}

enum class CompletedTournamentTab(val tabName: String) {
    MOOD_MATCH("무드매치"), MISSION("미션")
}