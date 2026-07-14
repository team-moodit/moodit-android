package com.swyp.moodit.analytics.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.swyp.moodit.analytics.AnalyticsEvent
import com.swyp.moodit.analytics.AnalyticsHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAnalyticsHelper @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsHelper {

    override fun logEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.type) {
            event.extras.forEach { param ->
                param(param.key, param.value)
            }
        }
    }

    override fun setUserEmail(email: String) {
        firebaseAnalytics.setUserId(email)
    }
}