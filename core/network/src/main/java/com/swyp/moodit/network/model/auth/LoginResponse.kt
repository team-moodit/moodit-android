package com.swyp.moodit.network.model.auth

data class LoginResponse(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
)
