package com.swyp.moodit.network

import com.swyp.moodit.datastore.token.AuthDataStore
import com.swyp.moodit.datastore.userPreference.UserPreferencesDataStore
import com.swyp.moodit.network.api.AuthApi
import com.swyp.moodit.network.model.auth.RefreshTokenRequest
import com.swyp.moodit.network.model.getOrThrow
import dagger.Lazy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val authDataStore: AuthDataStore,
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val authApi: Lazy<AuthApi>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        Timber.d("AuthAuthenticator 시작 - 요청 주소: ${response.request.url.encodedPath}")
        Timber.d("AuthAuthenticator 시작 - 이전 요청: ${response.priorResponse}")
        Timber.d("AuthAuthenticator 시작 - 이전 코드: ${response.priorResponse?.code}")

        // 리프레시 토큰 요청이 이미 실패한 경우 (무한 루프 방지)
        if (response.priorResponse != null && response.priorResponse?.code == 401) {
            Timber.e("리프레시 토큰이 유효하지 않습니다.")
            runBlocking {
                authDataStore.clearToken()
                userPreferencesDataStore.setAutoLoginEnabled(false)
            }
            return null
        }

        return runBlocking {
            Timber.d("AuthAuthenticator 시작")
            val currentRefreshToken = authDataStore.refreshToken.first()

            if (currentRefreshToken.isNullOrBlank()) {
                authDataStore.clearToken()
                userPreferencesDataStore.setAutoLoginEnabled(false)
                return@runBlocking null
            }

            try {
                Timber.d("서버에 토큰 재발급(Refresh) 요청 시도...")
                val newToken = authApi.get().refreshToken(RefreshTokenRequest(currentRefreshToken))
                    .getOrThrow()

                Timber.d("토큰 가져오기 성공: ${newToken.accessToken}")
                authDataStore.refreshAuthToken(
                    accessToken = newToken.accessToken,
                    refreshToken = newToken.refreshToken
                )

                return@runBlocking response.request.newBuilder()
                    .header("Authorization", "Bearer ${newToken.accessToken}")
                    .build()
            } catch (e: Exception) {
                Timber.e("토큰 재발급 실패: ${e.message}")
                authDataStore.clearToken()
                userPreferencesDataStore.setAutoLoginEnabled(false)
                return@runBlocking null
            }
        }
    }
}
