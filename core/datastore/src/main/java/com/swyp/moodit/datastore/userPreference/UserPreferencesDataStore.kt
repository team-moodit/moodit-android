package com.swyp.moodit.datastore.userPreference

import kotlinx.coroutines.flow.Flow

interface UserPreferencesDataStore {
    val isOnBoardingCompleted: Flow<Boolean>
    val isAutoLoginEnabled: Flow<Boolean>
    val nickname: Flow<String>
    suspend fun setOnBoardingCompleted(completed: Boolean)
    suspend fun setAutoLoginEnabled(enabled: Boolean)
    suspend fun setNickname(nickname: String)
}