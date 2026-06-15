package com.swyp.moodit.onboard.selectTaste

import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class SelectTasteContract {
    data object State : UiState

    sealed interface SideEffect : UiSideEffect {
        object NavigateToReport : SideEffect
    }

    sealed interface Intent : UiIntent {
        object OnNextClick : Intent
    }
}