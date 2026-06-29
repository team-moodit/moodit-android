package com.swyp.moodit.navigation

import com.swyp.moodit.model.MissionStatus
import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute : Route {
    @Serializable
    data object Setting : HomeRoute

    @Serializable
    data class MissionDetail(
        val missionId: Long,
        val statusName: String = MissionStatus.DEFAULT.name
    ) : HomeRoute {
        val status: MissionStatus
            get() = runCatching { MissionStatus.valueOf(statusName) }.getOrDefault(MissionStatus.DEFAULT)
    }

    @Serializable
    data object ReportReady : HomeRoute
}