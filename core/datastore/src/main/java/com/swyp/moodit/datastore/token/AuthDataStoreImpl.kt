package com.swyp.moodit.datastore.token

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.swyp.moodit.datastore.PreferencesKey
import com.swyp.moodit.datastore.model.AuthInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AuthDataStore {
    override val accessToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[PreferencesKey.ACCESS_TOKEN_KEY]
        }

    override val refreshToken: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[PreferencesKey.REFRESH_TOKEN_KEY]
        }

    override val userId: Flow<Long?>
        get() = dataStore.data.map { preferences ->
            preferences[PreferencesKey.USER_ID]
        }


    override suspend fun saveAuthInfo(authInfo: AuthInfo) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.ACCESS_TOKEN_KEY] = authInfo.accessToken
            preferences[PreferencesKey.REFRESH_TOKEN_KEY] = authInfo.refreshToken
            preferences[PreferencesKey.USER_ID] = authInfo.userId
        }
    }

    override suspend fun refreshAuthToken(accessToken: String, refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.ACCESS_TOKEN_KEY] = accessToken
            preferences[PreferencesKey.REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    override suspend fun clearToken() {
        dataStore.edit { preferences -> preferences.clear() }
    }
}