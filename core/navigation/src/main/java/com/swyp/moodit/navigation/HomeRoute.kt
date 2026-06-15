package com.swyp.moodit.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute : Route {
    @Serializable
    data object Setting : HomeRoute

    @Serializable
    data class MissionDetail(
        val missionId: String,
        val status: MissionStatus = MissionStatus.DEFAULT
    ) : HomeRoute

    @Serializable
    data object ReportReady : HomeRoute
}

@Serializable
enum class MissionStatus {
    DEFAULT,
    CREATED
}