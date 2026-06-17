package com.swyp.moodit.network.model

import retrofit2.Response
import timber.log.Timber

data class BaseResponse<T>(
    val success: T? = null,
    val error: Error? = null
)

data class Error(
    val errorCode: String,
    val message: String
)

fun <T> Response<BaseResponse<T>>.getOrThrow(): T {
    if (!this.isSuccessful) {
        Timber.d("서버 통신 실패: ${this.code()}")
        throw Exception("서버 통신 실패: ${this.code()}")
    }
    val baseResponse = this.body() ?: throw Exception("서버 응답 바디가 비어있습니다.")
    Timber.d("서버 응답: ${baseResponse.success}")
    if (baseResponse.success != null) {
        return baseResponse.success
    } else {
        Timber.d("서버 응답 에러: ${baseResponse.error}")
        throw Exception(baseResponse.error?.message ?: "알 수 없는 서버 에러")
    }
}

fun <T> Response<BaseResponse<T>>.getOrThrowUnit() {
    if (!this.isSuccessful) {
        Timber.d("서버 통신 실패: ${this.code()}")
        throw Exception("서버 통신 실패: ${this.code()}")
    }
    val baseResponse = this.body() ?: return
    if (baseResponse.error != null) {
        throw Exception(baseResponse.error.message)
    }
}
