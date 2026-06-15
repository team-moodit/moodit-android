package com.swyp.moodit.data.repository

import android.content.Context
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.model.AuthToken

interface AuthRepository {
    suspend fun loginWithKakao(context: Context): Result<AuthToken>
}