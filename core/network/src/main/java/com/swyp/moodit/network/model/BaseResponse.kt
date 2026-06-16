package com.swyp.moodit.network.model

data class BaseResponse<T>(
    val success: T? = null,
    val error: Error? = null
)

data class Error(
    val errorCode: String,
    val message: String
)
