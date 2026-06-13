package com.swyp.moodit.home.createRound

import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateRoundViewModel @Inject constructor(): BaseViewModel<CreateRoundContract.State, CreateRoundContract.Intent, CreateRoundContract.SideEffect>(
    initialState = CreateRoundContract.State()
) {
    override fun handleIntents(intent: CreateRoundContract.Intent) {
        TODO("Not yet implemented")
    }
}