package com.swyp.moodit.data.repository

import com.swyp.moodit.common.util.Result
import com.swyp.moodit.model.UserPrivacy
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val nickname: Flow<String>
    suspend fun getUserPrivacyInfo(): Result<UserPrivacy>
    suspend fun postNickname(nickname: String): Result<Unit>
}