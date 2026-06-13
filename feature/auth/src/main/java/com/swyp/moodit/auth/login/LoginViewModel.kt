package com.swyp.moodit.auth.login

import androidx.lifecycle.viewModelScope
import com.swyp.moodit.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() :
    BaseViewModel<LoginContract.State, LoginContract.Intent, LoginContract.SideEffect>(
        initialState = LoginContract.State()
    ) {
    override fun handleIntents(intent: LoginContract.Intent) {
        when (intent) {
            is LoginContract.Intent.OnLoginClick -> {
                loginOperation()
            }
        }
    }

    private var clickCount = 0
    fun loginOperation() {
        viewModelScope.launch {
            reduce { it.copy(isLoading = true) }
            delay(3000L)
            clickCount++
            val loginResult = clickCount > 1
            if (loginResult) {
                reduce { it.copy(isLoading = false) }
                sendEffect(LoginContract.SideEffect.NavigateToMain)
            } else {
                reduce { it.copy(isLoading = false) }
                sendEffect(LoginContract.SideEffect.ShowSnackbar("로그인에 실패했습니다."))
            }
        }
    }
}