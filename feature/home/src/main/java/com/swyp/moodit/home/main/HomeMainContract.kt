package com.swyp.moodit.home.main

import androidx.paging.PagingData
import com.swyp.moodit.model.Mission
import com.swyp.moodit.model.MissionStatus
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HomeMainContract {
    data class State(
        val isLoading: Boolean = false,
        val nickname: String = "",
        val inProgressMissions: Flow<PagingData<Mission>> = flowOf(PagingData.empty()),
        val completedMissions: Flow<PagingData<Mission>> = flowOf(PagingData.empty()),
        val feedbackSubMittedMissions: Flow<PagingData<Mission>> = flowOf(PagingData.empty()),
        val showResumeTournamentDialog: Boolean = false,
        val resumeTournamentId: Long = -1L
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        object NavigateToSetting : SideEffect
        object NavigateToCreateTournament : SideEffect
        data class NavigateToMatchUp(val tournamentId: Long) : SideEffect
        data class NavigateToMissionDetail(val missionId: Long, val status: MissionStatus) :
            SideEffect

        data class ShowSnackbar(val message: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        object OnSettingClick : Intent
        object OnCreateTournamentClick : Intent
        object OnDismissTournamentDialog : Intent
        object CheckOnGoingTournament : Intent
        data class OnResumeTournamentClick(val tournamentId: Long) : Intent
        data class OnMissionClick(val missionId: Long) : Intent
    }
}