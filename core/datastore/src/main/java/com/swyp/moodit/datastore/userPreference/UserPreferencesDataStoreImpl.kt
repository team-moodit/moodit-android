package com.swyp.moodit.datastore.userPreference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.swyp.moodit.datastore.PreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesDataStore {
    override val isOnBoardingCompleted: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[PreferencesKey.OnBoarding_Completed] ?: false
        }

    override val isAutoLoginEnabled: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[PreferencesKey.AutoLogin_Enabled] ?: false
        }

    override val nickname: Flow<String>
        get() = dataStore.data.map { preferences ->
            preferences[PreferencesKey.Nickname] ?: ""
        }

    override suspend fun setOnBoardingCompleted(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.OnBoarding_Completed] = completed
        }
    }

    override suspend fun setAutoLoginEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.AutoLogin_Enabled] = enabled
        }
    }

    override suspend fun setNickname(nickname: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.Nickname] = nickname
        }
    }
}