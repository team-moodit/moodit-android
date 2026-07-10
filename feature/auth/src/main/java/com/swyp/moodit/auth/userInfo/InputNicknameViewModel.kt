package com.swyp.moodit.auth.userInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.UserRepository
import com.swyp.moodit.designsystem.component.MooditSnackbarType
import com.swyp.moodit.navigation.AuthRoute
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputNicknameViewModel @Inject constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<InputNicknameContract.State, InputNicknameContract.Intent, InputNicknameContract.SideEffect>(
    initialState = InputNicknameContract.State(isEditMode = savedStateHandle.toRoute<AuthRoute.InputNickname>().isEditMode)
) {
    init {
        if (currentState.isEditMode) getUserPrivacyInfo()
    }

    override fun handleIntents(intent: InputNicknameContract.Intent) {
        when (intent) {
            is InputNicknameContract.Intent.OnNicknameChange -> {
                updateNickname(intent.nickname)
            }

            is InputNicknameContract.Intent.OnConfirmClick -> {
                postNickname()
            }
        }
    }

    private fun postNickname() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when (val result = userRepository.postNickname(currentState.nickname)) {
                is Result.Success -> {
                    if (currentState.isEditMode) {
                        sendEffect(InputNicknameContract.SideEffect.NavigateToSetting)
                    } else {
                        sendEffect(InputNicknameContract.SideEffect.NavigateToMain)
                    }
                }

                is Result.Error -> {
                    sendEffect(
                        InputNicknameContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "닉네임 등록에 실패했습니다.", MooditSnackbarType.ERROR
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }

    private fun getUserPrivacyInfo() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            when (val result = userRepository.getUserPrivacyInfo()) {
                is Result.Success -> {
                    reduce { it.copy(nickname = result.data.name.replace("\"", "")) }
                    checkNicknameCondition()
                }

                is Result.Error -> {
                    sendEffect(
                        InputNicknameContract.SideEffect.ShowSnackbar(
                            result.exception.message ?: "닉네임 조회에 실패했습니다.", MooditSnackbarType.ERROR
                        )
                    )
                }
            }
            reduce { it.copy(isLoading = false) }
        }
    }

    private fun updateNickname(nickname: String) {
        if (nickname.length <= MAX_NICKNAME_LENGTH && nickname.matches(INPUT_NICKNAME_REGEX.toRegex())) {
            reduce { it.copy(nickname = nickname) }
            checkNicknameCondition()
        }
    }

    private fun checkNicknameCondition() {
        val lengthCondition =
            currentState.nickname.length in MIN_NICKNAME_LENGTH..MAX_NICKNAME_LENGTH
        val nicknameRegex = NICKNAME_REGEX.toRegex()
        val nicknameCondition = currentState.nickname.matches(nicknameRegex)
        val isNicknameValid = lengthCondition && nicknameCondition
        reduce { it.copy(isNicknameValid = isNicknameValid) }
    }

    companion object {
        const val MIN_NICKNAME_LENGTH = 1
        const val MAX_NICKNAME_LENGTH = 6
        const val NICKNAME_REGEX = "^[ㄱ-ㅎㅏ-ㅣ가-힣]+$"
        const val INPUT_NICKNAME_REGEX = "^[ㄱ-ㅎㅏ-ㅣ가-힣\\u318D\\u119E\\u11A2\\u2022\\u2024\\u00B7\\u2219.]*$"
    }
}