package com.swyp.moodit.tournament.main

import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class TournamentMainContract {
    data class State(
        val isLoading: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data class NavigateToTournamentDetail(val tournamentId: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class OnTournamentClick(val tournamentId: String) : Intent
    }
}