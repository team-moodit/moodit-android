package com.swyp.moodit.tournament.create

import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class CreateTournamentContract {
    data class State(
        val isLoading: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data object NavigateToMatchUp : SideEffect
    }

    sealed interface Intent : UiIntent {
        object OnCreateTournamentClick : Intent
    }
}