package com.swyp.moodit.onboard.saveTaste

import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SaveTasteViewModel @Inject constructor() :
    BaseViewModel<SaveTasteContract.State, SaveTasteContract.Intent, SaveTasteContract.SideEffect>(
        initialState = SaveTasteContract.State
    ) {
    override fun handleIntents(intent: SaveTasteContract.Intent) {
        when (intent) {
            is SaveTasteContract.Intent.OnNextClick -> {
                sendEffect(SaveTasteContract.SideEffect.NavigateToSelectTaste)
            }
        }
    }
}