package com.swyp.moodit.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface OnBoardingRoute : Route {
    @Serializable
    data object SaveTaste : OnBoardingRoute
    @Serializable
    data object SelectTaste : OnBoardingRoute
    @Serializable
    data object Report : OnBoardingRoute
}