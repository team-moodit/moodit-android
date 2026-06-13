package com.swyp.moodit.home.main

import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class HomeMainContract {
    data class State(
        val isLoading: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        object NavigateToSetting : SideEffect
        object NavigateToCreateRound: SideEffect
        data class ShowSnackbar(val message: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        object OnSettingClick : Intent
        object OnCreateRoundClick : Intent
    }
}