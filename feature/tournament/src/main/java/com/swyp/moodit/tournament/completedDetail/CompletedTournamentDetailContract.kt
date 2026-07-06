package com.swyp.moodit.tournament.completedDetail

import com.swyp.moodit.model.tournament.CompletedTournamentDetail
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class CompletedTournamentDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val tournamentId: Long = 0L,
        val tournamentDetail: CompletedTournamentDetail = CompletedTournamentDetail(),
        val selectedTab: CompletedTournamentTab = CompletedTournamentTab.MOOD_MATCH
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class SelectTab(val tab: CompletedTournamentTab) : Intent
    }
}

enum class CompletedTournamentTab(val tabName: String) {
    MOOD_MATCH("무드매치"), MISSION("미션")
}