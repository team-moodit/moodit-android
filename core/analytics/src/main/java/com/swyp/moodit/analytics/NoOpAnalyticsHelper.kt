package com.swyp.moodit.analytics

class NoOpAnalyticsHelper : AnalyticsHelper {
    override fun logEvent(event: AnalyticsEvent) = Unit

    override fun setUserEmail(email: String) = Unit
}