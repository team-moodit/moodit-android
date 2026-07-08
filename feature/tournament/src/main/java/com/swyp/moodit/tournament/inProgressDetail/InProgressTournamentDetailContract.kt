package com.swyp.moodit.tournament.inProgressDetail

import com.swyp.moodit.model.tournament.InProgressTournamentDetail
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class InProgressTournamentDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val tournamentId: Long = 0L,
        val tournamentDetail: InProgressTournamentDetail = InProgressTournamentDetail()
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        data object OnDeleteTournamentClick : Intent
    }
}