package com.swyp.moodit.tournament.matchUp

import com.swyp.moodit.tournament.create.CreateTournamentContract
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatchUpViewModel @Inject constructor() :
    BaseViewModel<MatchUpContract.State, MatchUpContract.Intent, MatchUpContract.SideEffect>(
        initialState = MatchUpContract.State()
    ) {
    override fun handleIntents(intent: MatchUpContract.Intent) {
        TODO("Not yet implemented")
    }
}