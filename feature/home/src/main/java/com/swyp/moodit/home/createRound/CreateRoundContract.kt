package com.swyp.moodit.home.createRound

import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class CreateRoundContract {
    data class State(
        val isLoading: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data class ShowSnackbar(val message: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        object OnLoginClick : Intent
    }
}