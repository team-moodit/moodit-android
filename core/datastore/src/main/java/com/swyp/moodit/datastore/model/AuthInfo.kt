package com.swyp.moodit.datastore.model

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long
)
