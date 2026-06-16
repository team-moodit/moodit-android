package com.swyp.moodit.auth

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class KakaoAuth @Inject constructor() {
    suspend fun login(activityContext: Context): String =
        suspendCancellableCoroutine { continuation ->
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    // 웹 로그인 실패 시 에러 발생
                    continuation.resumeWithException(error)
                } else if (token != null) {
                    continuation.resume(token.accessToken)
                }
            }

            // 카카오톡 앱 실행 가능한 환경인지 확인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(activityContext)) {
                // 카카오톡 앱으로 로그인 시도
                UserApiClient.instance.loginWithKakaoTalk(activityContext) { token, error ->
                    // error 발생한 경우
                    if (error != null) {
                        // 사용자 취소 or 뒤로가기 등
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            continuation.resumeWithException(error)
                            return@loginWithKakaoTalk
                        }

                        // 취소가 아닌 앱 자체의 오류인 경우 (통신 오류 or 버그)
                        UserApiClient.instance.loginWithKakaoAccount(
                            activityContext,
                            callback = callback
                        )
                    } else if (token != null) {
                        // 카카오톡 앱으로 로그인 성공
                        continuation.resume(token.accessToken)
                    }
                }
            } else {
                // 카카오톡 앱이 없는 경우
                // 카카오톡 웹 로그인 창 출력
                UserApiClient.instance.loginWithKakaoAccount(activityContext, callback = callback)
            }
        }
}