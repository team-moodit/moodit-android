package com.swyp.moodit.data.repository

import com.swyp.moodit.common.util.Result
import com.swyp.moodit.model.UserPrivacy

interface UserRepository {
    suspend fun getUserPrivacyInfo(): Result<UserPrivacy>
}