package com.swyp.moodit.datastore.userPreference

import kotlinx.coroutines.flow.Flow

interface UserPreferencesDataStore {
    val isOnBoardingCompleted: Flow<Boolean>
    val isAutoLoginEnabled: Flow<Boolean>
    val nickname: Flow<String>
    val onGoingTournamentId: Flow<Long>
    val onGoingMatchResultId: Flow<Long>
    suspend fun setOnBoardingCompleted(completed: Boolean)
    suspend fun setAutoLoginEnabled(enabled: Boolean)
    suspend fun setNickname(nickname: String)
    suspend fun setOnGoingTournamentId(tournamentId: Long)
    suspend fun setOnGoingMatchResultId(matchResultId: Long)
    suspend fun clearOnGoingTournamentId()
    suspend fun clearOnGoingMatchResultId()
    suspend fun clearUserPreference()
}