package com.swyp.moodit.auth.login

import android.content.Context
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class LoginContract {
    data class State(
        val isLoading: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        object NavigateToMain : SideEffect
        data class NavigateToInputNickname(val isEditMode: Boolean) : SideEffect
        data class ShowSnackbar(val message: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        data class OnLoginClick(val context: Context) : Intent
    }
}