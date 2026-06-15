package com.swyp.moodit.onboard.saveTaste

import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class SaveTasteContract {
    data object State : UiState

    sealed interface SideEffect : UiSideEffect {
        object NavigateToSelectTaste : SideEffect
    }

    sealed interface Intent : UiIntent {
        object OnNextClick : Intent
    }
}