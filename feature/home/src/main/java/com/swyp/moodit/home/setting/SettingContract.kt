package com.swyp.moodit.home.setting

import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class SettingContract {
    data class State(
        val isLoading: Boolean = false,
        val dialogType: DialogType? = null,
        val name: String = "",
        val email: String = ""
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        object NavigateToLogin : SideEffect
        data class ShowSnackbar(val message: String) : SideEffect
    }

    sealed interface Intent : UiIntent {
        object OnTermsClick : Intent
        object OnPrivacyPolicyClick : Intent
        object OnFeedbackClick : Intent
        object ShowLogOutDialog : Intent
        object ShowDeleteAccountDialog : Intent
        object ConfirmLogOut : Intent
        object ConfirmDeleteAccount : Intent
        object ConfirmCompleteDeleteAccount : Intent
        object DismissDialog : Intent
    }

    enum class DialogType {
        LOGOUT,
        DELETE_ACCOUNT,
        COMPLETE_DELETE_ACCOUNT
    }
}