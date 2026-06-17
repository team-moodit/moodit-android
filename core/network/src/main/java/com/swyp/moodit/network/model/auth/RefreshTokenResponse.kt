package com.swyp.moodit.network.model.auth

data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String
)
