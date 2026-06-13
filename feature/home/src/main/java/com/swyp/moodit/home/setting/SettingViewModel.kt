package com.swyp.moodit.home.setting

import androidx.lifecycle.viewModelScope
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() :
    BaseViewModel<SettingContract.State, SettingContract.Intent, SettingContract.SideEffect>(
        initialState = SettingContract.State()
    ) {
    override fun handleIntents(intent: SettingContract.Intent) {
        when (intent) {
            is SettingContract.Intent.OnLogOutClick -> {
                logOut()
            }

            is SettingContract.Intent.OnDeleteAccountClick -> {
                deleteAccount()
            }
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            delay(3000L)
            val logOutResult = true
            if (logOutResult) {
                reduce { it.copy(isLoading = false) }
                sendEffect(SettingContract.SideEffect.NavigateToLogin)
            } else {
                reduce { it.copy(isLoading = false) }
                sendEffect(SettingContract.SideEffect.ShowSnackbar("로그아웃에 실패했습니다."))
            }
        }
    }

    private fun deleteAccount() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            delay(3000L)
            val deleteAccountResult = false
            if (deleteAccountResult) {
                reduce { it.copy(isLoading = false) }
                sendEffect(SettingContract.SideEffect.NavigateToLogin)
            } else {
                reduce { it.copy(isLoading = false) }
                sendEffect(SettingContract.SideEffect.ShowSnackbar("계정 삭제에 실패했습니다."))
            }
        }
    }
}