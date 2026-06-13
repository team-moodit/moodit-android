package com.swyp.moodit.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute : Route {
    @Serializable
    data object Setting : HomeRoute

    @Serializable
    data object CreateRound : HomeRoute
}