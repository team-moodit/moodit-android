package com.swyp.moodit.data.impl

import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.UserRepository
import com.swyp.moodit.model.UserPrivacy
import com.swyp.moodit.network.api.MooditApi
import com.swyp.moodit.network.model.getOrThrow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mooditApi: MooditApi
) : UserRepository {
    override suspend fun getUserPrivacyInfo(): Result<UserPrivacy> {
        return try {
            val result = mooditApi.getUserPrivacyInfo().getOrThrow()

            Result.Success(
                UserPrivacy(
                    name = result.name,
                    email = result.email
                )
            )
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}