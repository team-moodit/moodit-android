package com.swyp.moodit.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface BottomBarRoute: Route {
    @Serializable
    data object Home: BottomBarRoute
    @Serializable
    data object Tournament: BottomBarRoute
    @Serializable
    data object Report: BottomBarRoute
}