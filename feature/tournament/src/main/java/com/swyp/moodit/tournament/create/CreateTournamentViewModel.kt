package com.swyp.moodit.tournament.create

import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateTournamentViewModel @Inject constructor() :
    BaseViewModel<CreateTournamentContract.State, CreateTournamentContract.Intent, CreateTournamentContract.SideEffect>(
        initialState = CreateTournamentContract.State()
    ) {
    override fun handleIntents(intent: CreateTournamentContract.Intent) {
        TODO("Not yet implemented")
    }
}