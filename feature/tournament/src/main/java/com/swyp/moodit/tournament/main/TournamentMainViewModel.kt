package com.swyp.moodit.tournament.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TournamentMainViewModel @Inject constructor(
    private val tournamentRepository: TournamentRepository
) :
    BaseViewModel<TournamentMainContract.State, TournamentMainContract.Intent, TournamentMainContract.SideEffect>(
        initialState = TournamentMainContract.State()
    ) {

    init {
        loadTournaments()
    }

    override fun handleIntents(intent: TournamentMainContract.Intent) {
        when (intent) {
            is TournamentMainContract.Intent.OnInProgressTournamentClick -> {
                sendEffect(
                    TournamentMainContract.SideEffect.NavigateToInProgressTournamentDetail(
                        intent.tournamentId,
                    )
                )
            }

            is TournamentMainContract.Intent.OnCompletedTournamentClick -> {
                sendEffect(
                    TournamentMainContract.SideEffect.NavigateToCompletedTournamentDetail(intent.tournamentId, intent.userMissionId)
                )
            }

            is TournamentMainContract.Intent.OnSettingClick -> {
                sendEffect(TournamentMainContract.SideEffect.NavigateToSetting)
            }
        }
    }

    private fun loadTournaments() {
        val inProgressTournamentsFlow =
            tournamentRepository.getPagingInProgressTournaments().cachedIn(viewModelScope)
        val completedTournamentFlow =
            tournamentRepository.getPagingCompletedTournaments().cachedIn(viewModelScope)

        reduce {
            it.copy(
                inProgressTournaments = inProgressTournamentsFlow,
                completedTournaments = completedTournamentFlow
            )
        }
    }
}