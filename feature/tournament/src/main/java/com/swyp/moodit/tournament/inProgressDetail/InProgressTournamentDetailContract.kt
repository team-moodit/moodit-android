package com.swyp.moodit.tournament.inProgressDetail

import com.swyp.moodit.model.tournament.InProgressMatchState
import com.swyp.moodit.model.tournament.InProgressTournamentDetail
import com.swyp.moodit.tournament.main.TournamentMainContract
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class InProgressTournamentDetailContract {
    data class State(
        val isLoading: Boolean = false,
        val tournamentId: Long = 0L,
        val matchResultId: Long = 0L,
        val matchState: InProgressMatchState = InProgressMatchState.ING,
        val tournamentDetail: InProgressTournamentDetail = InProgressTournamentDetail(),
        val showDeleteDialog: Boolean = false,
        val showDeleteCompleteDialog: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data object NavigateToTournament: SideEffect
        data class NavigateToMatchUp(val tournamentId: Long, val isStarted: Boolean) : SideEffect
        data class NavigateToMoodMatchResult(val matchResultId: Long) : SideEffect

    }

    sealed interface Intent : UiIntent {
        data object OnDeleteTournamentClick : Intent
        data object OnResumeTournamentClick : Intent
        data object OnDeleteTournamentCompleteClick : Intent
        data class OnDeleteDialogShowChange(val show: Boolean) : Intent
        data class OnDeleteCompleteDialogShowChange(val show: Boolean) : Intent
    }
}