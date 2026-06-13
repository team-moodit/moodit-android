package com.swyp.moodit.onboard.selectTaste

import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectTasteViewModel @Inject constructor() :
    BaseViewModel<SelectTasteContract.State, SelectTasteContract.Intent, SelectTasteContract.SideEffect>(
        initialState = SelectTasteContract.State
    ) {
    override fun handleIntents(intent: SelectTasteContract.Intent) {
        when (intent) {
            is SelectTasteContract.Intent.OnNextClick -> {
                sendEffect(SelectTasteContract.SideEffect.NavigateToReport)
            }
        }
    }
}