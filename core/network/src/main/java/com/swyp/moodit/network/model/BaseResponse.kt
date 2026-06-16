package com.swyp.moodit.network.model

import retrofit2.Response

data class BaseResponse<T>(
    val success: T? = null,
    val error: Error? = null
)

data class Error(
    val errorCode: String,
    val message: String
)

inline fun <T> Response<BaseResponse<T>>.getOrThrow(): T {
    if (!this.isSuccessful) {
        throw Exception("서버 통신 실패: ${this.code()}")
    }
    val baseResponse = this.body() ?: throw Exception("서버 응답 바디가 비어있습니다.")
    if (baseResponse.success != null) {
        return baseResponse.success
    } else {
        throw Exception(baseResponse.error?.message ?: "알 수 없는 서버 에러")
    }
}
