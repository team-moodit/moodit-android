package com.swyp.moodit.data.repository

import com.swyp.moodit.common.util.Result

interface UserRepository {
    suspend fun getUserProfile(): Result<String>
}