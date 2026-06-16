package com.swyp.moodit.network

import com.swyp.moodit.datastore.token.AuthDataStore
import com.swyp.moodit.network.api.MooditApi
import com.swyp.moodit.network.model.auth.RefreshTokenRequest
import com.swyp.moodit.network.model.getOrThrow
import dagger.Lazy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val authDataStore: AuthDataStore,
    private val mooditApi: Lazy<MooditApi>
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val currentRefreshToken = authDataStore.refreshToken.first()
            if (currentRefreshToken.isNullOrBlank()) return@runBlocking null
            try {
                val newToken =
                    mooditApi.get().refreshToken(RefreshTokenRequest(currentRefreshToken)).getOrThrow()
                authDataStore.refreshAuthToken(
                    accessToken = newToken.accessToken,
                    refreshToken = newToken.refreshToken
                )
                return@runBlocking response.request.newBuilder()
                    .header("Authorization", "Bearer ${newToken.accessToken}")
                    .build()
            } catch (e: Exception) {
                authDataStore.clearToken()
                return@runBlocking null
            }
        }
    }
}