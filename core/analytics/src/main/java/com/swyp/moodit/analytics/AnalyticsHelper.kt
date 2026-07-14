package com.swyp.moodit.analytics

interface AnalyticsHelper {
    fun logEvent(event: AnalyticsEvent)
}