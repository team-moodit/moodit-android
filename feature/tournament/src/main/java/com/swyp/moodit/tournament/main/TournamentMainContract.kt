package com.swyp.moodit.tournament.main

import androidx.paging.PagingData
import com.swyp.moodit.model.tournament.CompletedTournament
import com.swyp.moodit.model.tournament.InProgressMatchState
import com.swyp.moodit.model.tournament.InProgressTournament
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TournamentMainContract {
    data class State(
        val isLoading: Boolean = false,
        val inProgressTournaments: Flow<PagingData<InProgressTournament>> = flowOf(PagingData.empty()),
        val completedTournaments: Flow<PagingData<CompletedTournament>> = flowOf(PagingData.empty())
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
        data class NavigateToInProgressTournamentDetail(
            val tournamentId: Long,
            val matchResultId: Long,
            val matchState: InProgressMatchState
        ) : SideEffect

        data class NavigateToCompletedTournamentDetail(
            val tournamentId: Long,
            val userMissionId: Long
        ) : SideEffect

        data object NavigateToSetting : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class OnInProgressTournamentClick(val tournamentId: Long, val matchResultId: Long, val matchState: InProgressMatchState) :
            Intent

        data class OnCompletedTournamentClick(val tournamentId: Long, val userMissionId: Long) :
            Intent

        data object OnSettingClick : Intent
    }
}