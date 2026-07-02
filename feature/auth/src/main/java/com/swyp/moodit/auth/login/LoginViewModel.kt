package com.swyp.moodit.auth.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.AuthRepository
import com.swyp.moodit.data.repository.UserRepository
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : BaseViewModel<LoginContract.State, LoginContract.Intent, LoginContract.SideEffect>(
    initialState = LoginContract.State()
) {
    override fun handleIntents(intent: LoginContract.Intent) {
        when (intent) {
            is LoginContract.Intent.OnLoginClick -> {
                loginOperation(intent.context)
                //sendEffect(LoginContract.SideEffect.NavigateToInputNickname(false))
            }
        }
    }

    fun loginOperation(context: Context) {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            try {
                when (val kakaoResult = authRepository.loginWithKakao(context)) {
                    is Result.Success -> {
                        val accessToken = kakaoResult.data
                        when (val loginResult = authRepository.loginWithServer(accessToken)) {
                            is Result.Success -> {
                                val savedNickname = userRepository.nickname.first()
                                if (savedNickname.isEmpty()) {
                                    sendEffect(
                                        LoginContract.SideEffect.NavigateToInputNickname(
                                            false
                                        )
                                    )
                                } else {
                                    sendEffect(LoginContract.SideEffect.NavigateToMain)
                                }
                                return@launch
                            }

                            is Result.Error -> {
                                sendEffect(
                                    LoginContract.SideEffect.ShowSnackbar(
                                        loginResult.exception.message ?: "서버 로그인에 실패했습니다."
                                    )
                                )
                            }
                        }
                    }

                    is Result.Error -> {
                        sendEffect(
                            LoginContract.SideEffect.ShowSnackbar(
                                kakaoResult.exception.message ?: "카카오 로그인에 실패했습니다."
                            )
                        )
                    }
                }
            } finally {
                reduce { it.copy(isLoading = false) }
            }
        }
    }
}