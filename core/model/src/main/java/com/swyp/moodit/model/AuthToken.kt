package com.swyp.moodit.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String
)
