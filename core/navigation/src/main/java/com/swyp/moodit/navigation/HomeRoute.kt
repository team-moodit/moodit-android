package com.swyp.moodit.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute : Route {
    @Serializable
    data object Setting : HomeRoute

    @Serializable
    data class MissionDetail(
        val missionId: String
    ) : HomeRoute

    @Serializable
    data object ReportReady : HomeRoute
}