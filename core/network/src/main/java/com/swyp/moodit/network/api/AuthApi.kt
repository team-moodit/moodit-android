package com.swyp.moodit.network.api

import com.swyp.moodit.network.model.BaseResponse
import com.swyp.moodit.network.model.auth.LoginRequest
import com.swyp.moodit.network.model.auth.LoginResponse
import com.swyp.moodit.network.model.auth.RefreshTokenRequest
import com.swyp.moodit.network.model.auth.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("v1/auth/kakao-login")
    suspend fun kakaoLogin(
        @Body request: LoginRequest
    ): Response<BaseResponse<LoginResponse>>

    @POST("v1/auth/refresh")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<BaseResponse<RefreshTokenResponse>>
}
