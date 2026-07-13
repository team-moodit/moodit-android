package com.swyp.moodit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swyp.moodit.datastore.userPreference.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(MainUIState())
    val state: StateFlow<MainUIState> = _state.asStateFlow()

    private val _sideEffect = Channel<MainSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        checkState()
    }

    private fun checkState() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val isOnBoardingCompleted = userPreferencesDataStore.isOnBoardingCompleted.first()
            val isAutoLoginEnabled = userPreferencesDataStore.isAutoLoginEnabled.first()
            val isNicknameSet = userPreferencesDataStore.nickname.first().isNotBlank()
            delay(2000L)
            when {
                !isOnBoardingCompleted -> _sideEffect.send(MainSideEffect.NavigateToOnBoarding)
                !isAutoLoginEnabled -> _sideEffect.send(MainSideEffect.NavigateToLogin)
                !isNicknameSet -> _sideEffect.send(MainSideEffect.NavigateToInputNickname)
                else -> _sideEffect.send(MainSideEffect.NavigateToHome)
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}

data class MainUIState(
    val isLoading: Boolean = false,
)

sealed interface MainSideEffect {
    data object NavigateToLogin : MainSideEffect
    data object NavigateToHome : MainSideEffect
    data object NavigateToOnBoarding : MainSideEffect
    data object NavigateToInputNickname : MainSideEffect
}
