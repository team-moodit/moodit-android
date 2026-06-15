package com.swyp.moodit.data.impl

import android.content.Context
import com.swyp.moodit.auth.KakaoAuth
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.AuthRepository
import com.swyp.moodit.model.AuthToken
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val kakaoAuth: KakaoAuth
) : AuthRepository {
    override suspend fun loginWithKakao(context: Context): Result<AuthToken> {
        return try {
            val authToken = kakaoAuth.login(context)
            Result.Success(authToken)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}