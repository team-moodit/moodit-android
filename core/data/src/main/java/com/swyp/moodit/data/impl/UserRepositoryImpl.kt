package com.swyp.moodit.data.impl

import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.UserRepository
import com.swyp.moodit.datastore.userPreference.UserPreferencesDataStore
import com.swyp.moodit.model.UserPrivacy
import com.swyp.moodit.network.api.MooditApi
import com.swyp.moodit.network.model.getOrThrow
import com.swyp.moodit.network.model.getOrThrowUnit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mooditApi: MooditApi,
    private val userPreferencesDataStore: UserPreferencesDataStore
) : UserRepository {
    override val nickname: Flow<String> = userPreferencesDataStore.nickname

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

    override suspend fun postNickname(nickname: String): Result<Unit> {
        return try {
            mooditApi.postNickname(request = nickname).getOrThrowUnit()
            userPreferencesDataStore.setNickname(nickname)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}