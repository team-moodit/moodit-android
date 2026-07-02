package com.swyp.moodit.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthRoute : Route {
    @Serializable
    data object Login : AuthRoute

    @Serializable
    data class InputNickname(val isEditMode: Boolean) : AuthRoute
}