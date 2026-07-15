package com.swyp.moodit.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

internal object PreferencesKey {
    val ACCESS_TOKEN_KEY = stringPreferencesKey("moodit_jwt_token")
    val REFRESH_TOKEN_KEY = stringPreferencesKey("moodit_jwt_refresh_token")
    val USER_ID = longPreferencesKey("moodit_user_id")
    val OnBoarding_Completed = booleanPreferencesKey("moodit_onboarding_completed")
    val AutoLogin_Enabled = booleanPreferencesKey("moodit_auto_login_enabled")
    val Nickname = stringPreferencesKey("moodit_nickname")
    val OnGoingTournamentId = longPreferencesKey("moodit_onGoing_tournament_id")
    val OnGoingMatchResultId = longPreferencesKey("moodit_onGoing_match_result_id")
    val Created_Match_Before = booleanPreferencesKey("moodit_created_match_before")
}