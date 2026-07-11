package com.swyp.moodit.data.repository

import android.content.Context
import com.swyp.moodit.common.util.Result

interface AuthRepository {
    suspend fun loginWithKakao(context: Context): Result<String>
    suspend fun loginWithServer(accessToken: String): Result<Unit>
    suspend fun logOut(): Result<Unit>
    suspend fun withdraw(): Result<Unit>
}