package com.swyp.moodit.datastore.token

import com.swyp.moodit.datastore.model.AuthInfo
import kotlinx.coroutines.flow.Flow

interface AuthDataStore {
    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>
    val userId: Flow<String?>
    suspend fun saveAuthInfo(authInfo: AuthInfo)
    suspend fun refreshAuthToken(accessToken: String, refreshToken: String)
    suspend fun clearToken()
}