package com.swyp.moodit.data.impl

import android.content.Context
import com.swyp.moodit.auth.KakaoAuth
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.AuthRepository
import com.swyp.moodit.datastore.model.AuthInfo
import com.swyp.moodit.datastore.token.AuthDataStore
import com.swyp.moodit.datastore.userPreference.UserPreferencesDataStore
import com.swyp.moodit.network.api.MooditApi
import com.swyp.moodit.network.model.auth.LoginRequest
import com.swyp.moodit.network.model.getOrThrow
import com.swyp.moodit.network.model.getOrThrowUnit
import timber.log.Timber
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val kakaoAuth: KakaoAuth,
    private val mooditApi: MooditApi,
    private val authDataStore: AuthDataStore,
    private val userPreferencesDataStore: UserPreferencesDataStore
) : AuthRepository {
    override suspend fun loginWithKakao(context: Context): Result<String> {
        return try {
            val authToken = kakaoAuth.login(context)
            Result.Success(authToken)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun loginWithServer(accessToken: String): Result<Unit> {
        return try {
            val response =
                mooditApi.kakaoLogin(LoginRequest(accessToken = accessToken)).getOrThrow()
            authDataStore.saveAuthInfo(
                AuthInfo(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                    userId = response.userId
                )
            )
            userPreferencesDataStore.setAutoLoginEnabled(true)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun logOut(): Result<Unit> {
        return try {
            mooditApi.logout().getOrThrowUnit()
            authDataStore.clearToken()
            userPreferencesDataStore.setAutoLoginEnabled(false)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}