package com.swyp.moodit.common.auth

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthEventManager @Inject constructor() {
    private val _authEvents = MutableSharedFlow<AuthEvent>()
    val authEvents = _authEvents.asSharedFlow()

    suspend fun onUnauthorized() {
        _authEvents.emit(AuthEvent.Unauthorized)
    }
}

sealed interface AuthEvent {
    data object Unauthorized : AuthEvent
}