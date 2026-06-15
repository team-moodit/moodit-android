package com.swyp.moodit.tournament.main

import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TournamentMainViewModel @Inject constructor() :
    BaseViewModel<TournamentMainContract.State, TournamentMainContract.Intent, TournamentMainContract.SideEffect>(
        initialState = TournamentMainContract.State()
    ) {

    override fun handleIntents(intent: TournamentMainContract.Intent) {
        when (intent) {
            is TournamentMainContract.Intent.OnTournamentClick -> {
                sendEffect(TournamentMainContract.SideEffect.NavigateToTournamentDetail(intent.tournamentId))
            }
        }
    }
}