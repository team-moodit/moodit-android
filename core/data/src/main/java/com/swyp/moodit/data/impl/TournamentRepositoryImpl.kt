package com.swyp.moodit.data.impl

import com.swyp.moodit.common.util.ImageProcessor
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.mapper.toModel
import com.swyp.moodit.data.mapper.toNetworkRequest
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.datastore.userPreference.UserPreferencesDataStore
import com.swyp.moodit.model.MatchUpInfo
import com.swyp.moodit.model.MatchUpResult
import com.swyp.moodit.model.PartType
import com.swyp.moodit.model.SelectedMatchUpIds
import com.swyp.moodit.model.SelectedPhoto
import com.swyp.moodit.model.UploadStatus
import com.swyp.moodit.network.api.MooditApi
import com.swyp.moodit.network.api.S3Api
import com.swyp.moodit.network.model.getOrThrow
import com.swyp.moodit.network.model.tournament.CreateMoodMatchRequest
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpInitRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import javax.inject.Inject

internal class TournamentRepositoryImpl @Inject constructor(
    private val imageProcessor: ImageProcessor,
    private val mooditApi: MooditApi,
    private val s3Api: S3Api,
    private val userDataStore: UserPreferencesDataStore
) : TournamentRepository {
    override val onGoingTournamentId: Flow<Long> = userDataStore.onGoingTournamentId

    override suspend fun uploadImage(photo: SelectedPhoto): Result<SelectedPhoto> {
        return withContext(Dispatchers.IO) {
            try {
                val file = imageProcessor.uriToFile(photo.uri)
                    ?: throw IllegalArgumentException("파일 변환 실패")
                val presignedResponse = mooditApi.getPresignedUrl(
                    resourceType = PartType.MATCH.name,
                    fileName = file.name
                ).getOrThrow()
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val s3Response = s3Api.uploadImageToS3(
                    url = presignedResponse.fileUrl,
                    contentType = "image/jpeg",
                    file = requestFile
                )
                if (s3Response.isSuccessful) {
                    val uploadedPhoto = photo.copy(
                        serverId = presignedResponse.id,
                        status = UploadStatus.Success(presignedResponse.fileUrl)
                    )
                    Result.Success(uploadedPhoto)
                } else {
                    Result.Error(HttpException(s3Response))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun createMoodMatch(title: String, imageIds: List<Long>): Result<Long> {
        try {
            val response =
                mooditApi.createMoodMatch(CreateMoodMatchRequest(title, imageIds)).getOrThrow()
            return Result.Success(response.matchId)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun getMatchUpInitInfo(matchId: Long): Result<MatchUpInfo> {
        try {
            val response =
                mooditApi.getMatchUpInitInfo(MatchUpInitRequest(matchId = matchId)).getOrThrow()
            return Result.Success(response.toModel())
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun saveMatchUp(
        matchId: Long,
        selectedMatchUpIds: SelectedMatchUpIds
    ): Result<Unit> {
        try {
            mooditApi.saveMatchUp(matchId, selectedMatchUpIds.toNetworkRequest()).getOrThrow()
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun setOnGoingTournamentId(tournamentId: Long): Result<Unit> {
        try {
            userDataStore.setOnGoingTournamentId(tournamentId)
            return Result.Success(Unit)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun getMatchUpProgressInfo(matchId: Long): Result<MatchUpInfo> {
        try {
            val response = mooditApi.getNextMatchUpInfo(matchId).getOrThrow()
            return Result.Success(response.toModel())
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun getMatchUpResult(matchId: Long): Result<MatchUpResult> {
        try {
            val response = mooditApi.getMatchUpResult(matchId).getOrThrow()
            return Result.Success(response.toModel())
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}