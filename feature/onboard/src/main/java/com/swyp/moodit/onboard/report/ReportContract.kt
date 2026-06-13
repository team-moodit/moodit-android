package com.swyp.moodit.onboard.report

import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class ReportContract {
    data object State : UiState

    sealed interface SideEffect : UiSideEffect {
        object NavigateToLogin : SideEffect
    }

    sealed interface Intent : UiIntent {
        object OnStartClick : Intent
    }
}