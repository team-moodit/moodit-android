package com.swyp.moodit.auth.userInfo

import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.ui.base.UiIntent
import com.swyp.moodit.ui.base.UiSideEffect
import com.swyp.moodit.ui.base.UiState

class InputNicknameContract {
    data class State(
        val isLoading: Boolean = false,
        val isEditMode: Boolean = false,
        val nickname: String = "",
        val isNicknameValid: Boolean = false
    ) : UiState

    sealed interface SideEffect : UiSideEffect {
        data object NavigateToMain : SideEffect
        data object NavigateToSetting : SideEffect
        data class ShowSnackbar(
            val message: String,
            val snackbarType: MooditSnackbarType = MooditSnackbarType.SUCCESS
        ) : SideEffect
    }

    sealed interface Intent : UiIntent {
        data object OnConfirmClick : Intent
        data class OnNicknameChange(val nickname: String) : Intent
    }
}