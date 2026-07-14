package com.swyp.moodit.analytics

data class AnalyticsEvent(
    val type: String,
    val extras: List<Param> = emptyList()
) {
    class Types {
        companion object {
            const val SCREEN_VIEW = "screen_view"
        }
    }
}

data class Param(val key: String, val value: String) {
    class Keys {
        companion object {
            const val SCREEN_NAME = "screen_name"
        }
    }
}
