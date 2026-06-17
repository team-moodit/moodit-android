package com.swyp.moodit.datastore.userPreference

import kotlinx.coroutines.flow.Flow

interface UserPreferencesDataStore {
    val isOnBoardingCompleted: Flow<Boolean>
    val isAutoLoginEnabled: Flow<Boolean>
    suspend fun setOnBoardingCompleted(completed: Boolean)
    suspend fun setAutoLoginEnabled(enabled: Boolean)
}