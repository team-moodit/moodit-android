package com.swyp.moodit.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : UiState, Intent : UiIntent, SideEffect : UiSideEffect>(
    initialState: State
) : ViewModel() {

    // 상태 관리
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    protected val currentState: State
        get() = _uiState.value

    // 사용자 액션/의도 수집 및 처리
    private val _intent = MutableSharedFlow<Intent>()
    val intent: SharedFlow<Intent> = _intent.asSharedFlow()

    // 일회성 이벤트 처리
    private val _sideEffect = Channel<SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        subscribeIntents()
    }

    protected abstract fun handleIntents(intent: Intent)

    private fun subscribeIntents() {
        viewModelScope.launch {
            _intent.collect {
                handleIntents(it)
            }
        }
    }

    fun sendIntent(intent: Intent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    protected fun reduce(reduce: (State) -> State) {
        _uiState.value = reduce(currentState)
    }

    protected fun sendEffect(effect: SideEffect) {
        viewModelScope.launch {
            _sideEffect.send(effect)
        }
    }
}