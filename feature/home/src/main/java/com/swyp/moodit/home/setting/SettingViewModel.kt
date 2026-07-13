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

    override fun handleIntents(intent: SettingContract.Intent) {
        when (intent) {
            is SettingContract.Intent.LoadUserInfo -> {
                getUserPrivacyInfo()
            }

            is SettingContract.Intent.OnTermsClick -> {
                sendEffect(SettingContract.SideEffect.NavigateToUrl(TERMS_OF_USE_URL))
            }

            is SettingContract.Intent.OnNicknameClick -> {
                sendEffect(SettingContract.SideEffect.NavigateToInputNickname(true))
            }

            is SettingContract.Intent.OnPrivacyPolicyClick -> {
                sendEffect(SettingContract.SideEffect.NavigateToUrl(PRIVACY_POLICY_URL))
            }

            is SettingContract.Intent.OnFeedbackClick -> {
                sendEffect(SettingContract.SideEffect.NavigateToUrl(FEEDBACK_URL))
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
                    reduce {
                        it.copy(
                            name = result.data.name,
                            email = result.data.email
                        )
                    }
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
            when (val result = authRepository.withdraw()) {
                is Result.Success -> {
                    reduce { it.copy(isLoading = false) }
                    updateDialogType(SettingContract.DialogType.COMPLETE_DELETE_ACCOUNT)
                }

                is Result.Error -> {
                    reduce { it.copy(isLoading = false) }
                    updateDialogType(null)
                    sendEffect(
                        SettingContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "계정 삭제에 실패했습니다."
                        )
                    )
                }
            }
        }
    }

    private fun updateDialogType(dialogType: SettingContract.DialogType?) {
        reduce { it.copy(dialogType = dialogType) }
    }

    companion object {
        const val PRIVACY_POLICY_URL =
            "https://app.notion.com/p/moodit-38946481fa1180bd8967dc8494bf97c1"
        const val TERMS_OF_USE_URL =
            "https://app.notion.com/p/moodit-38946481fa1180548122e37b17d5c981"
        const val FEEDBACK_URL =
            "https://docs.google.com/forms/d/e/1FAIpQLSeoOAJv_mK8RnV3s2UNf2sLDtiP917fz7nTfZk76TsPJ0sOow/viewform"
    }
}