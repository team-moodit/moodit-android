package com.swyp.moodit.tournament.detail

import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class TournamentDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val tournamentId: Long = 0L,
        val isCompleted: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        data object OnDeleteTournamentClick : Intent
    }
}