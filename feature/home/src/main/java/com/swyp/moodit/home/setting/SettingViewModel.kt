package com.swyp.moodit.home.setting

import androidx.lifecycle.viewModelScope
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.AuthRepository
import com.swyp.moodit.data.repository.UserRepository
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) :
    BaseViewModel<SettingContract.State, SettingContract.Intent, SettingContract.SideEffect>(
        initialState = SettingContract.State()
    ) {

    init {
        getUserPrivacyInfo()
    }

    override fun handleIntents(intent: SettingContract.Intent) {
        when (intent) {
            is SettingContract.Intent.OnTermsClick -> {
                sendEffect(SettingContract.SideEffect.ShowSnackbar("이용약관 출력하기"))
            }

            is SettingContract.Intent.OnPrivacyPolicyClick -> {
                sendEffect(SettingContract.SideEffect.ShowSnackbar("개인정보 처리방침 출력하기"))
            }

            is SettingContract.Intent.OnFeedbackClick -> {
                sendEffect(SettingContract.SideEffect.ShowSnackbar("피드백 사이트 이동하기"))
            }

            is SettingContract.Intent.ShowLogOutDialog -> {
                updateDialogType(SettingContract.DialogType.LOGOUT)
            }

            is SettingContract.Intent.ShowDeleteAccountDialog -> {
                updateDialogType(SettingContract.DialogType.DELETE_ACCOUNT)
            }

            is SettingContract.Intent.DismissDialog -> {
                updateDialogType(null)
            }

            is SettingContract.Intent.ConfirmLogOut -> {
                logOut()
            }

            is SettingContract.Intent.ConfirmDeleteAccount -> {
                deleteAccount()
            }

            is SettingContract.Intent.ConfirmCompleteDeleteAccount -> {
                sendEffect(SettingContract.SideEffect.NavigateToLogin)
            }
        }
    }

    private fun getUserPrivacyInfo() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when (val result = userRepository.getUserPrivacyInfo()) {
                is Result.Success -> {
                    reduce { it.copy(
                        name = result.data.name,
                        email = result.data.email
                    ) }
                }

                is Result.Error -> {
                    sendEffect(
                        SettingContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "유저 개인정보 조회에 실패했습니다."
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when (val logOutResult = authRepository.logOut()) {
                is Result.Success -> {
                    sendEffect(SettingContract.SideEffect.NavigateToLogin)
                }

                is Result.Error -> {
                    reduce { it.copy(isLoading = false) }
                    sendEffect(
                        SettingContract.SideEffect.ShowSnackbar(
                            logOutResult.exception.message ?: "로그아웃에 실패했습니다."
                        )
                    )
                }
            }
        }
    }

    private fun deleteAccount() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            val deleteAccountResult = true
            if (deleteAccountResult) {
                reduce { it.copy(isLoading = false) }
                updateDialogType(SettingContract.DialogType.COMPLETE_DELETE_ACCOUNT)
            } else {
                reduce { it.copy(isLoading = false) }
                updateDialogType(null)
                sendEffect(SettingContract.SideEffect.ShowSnackbar("계정 삭제에 실패했습니다."))
            }
        }
    }

    private fun updateDialogType(dialogType: SettingContract.DialogType?) {
        reduce { it.copy(dialogType = dialogType) }
    }
}