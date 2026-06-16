package com.swyp.moodit.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

internal object PreferencesKey {
    val ACCESS_TOKEN_KEY = stringPreferencesKey("moodit_jwt_token")
    val REFRESH_TOKEN_KEY = stringPreferencesKey("moodit_jwt_refresh_token")
    val USER_ID = stringPreferencesKey("moodit_user_id")
}