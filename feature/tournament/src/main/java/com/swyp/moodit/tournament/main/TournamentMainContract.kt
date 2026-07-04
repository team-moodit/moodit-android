package com.swyp.moodit.tournament.main

import com.swyp.moodit.model.tournament.CompletedTournamentDetail
import com.swyp.moodit.model.tournament.InProgressTournamentDetail
import com.swyp.moodit.model.tournament.TournamentState
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class TournamentMainContract {
    data class State(
        val isLoading: Boolean = false,
        val inProgressTournaments: List<InProgressTournamentDetail> = listOf(
            InProgressTournamentDetail(
                id = 1L,
                title = "출근 할 때 입을 옷",
                currentRound = "8강",
                imageUris = listOf(
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1",
                    "1"
                )
            )
        ),
        val completedTournaments: List<CompletedTournamentDetail> = emptyList()
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data class NavigateToTournamentDetail(
            val tournamentId: Long,
            val tournamentState: TournamentState
        ) : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class OnTournamentClick(val tournamentId: Long, val tournamentState: TournamentState) : Intent
    }
}