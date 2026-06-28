package com.swyp.moodit.data.impl

import com.swyp.moodit.common.util.ImageProcessor
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.mapper.toModel
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.model.MatchUpInfo
import com.swyp.moodit.model.PartType
import com.swyp.moodit.model.SelectedPhoto
import com.swyp.moodit.model.UploadStatus
import com.swyp.moodit.network.api.MooditApi
import com.swyp.moodit.network.model.getOrThrow
import com.swyp.moodit.network.model.tournament.CreateMoodMatchRequest
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpInitRequest
import com.swyp.moodit.network.model.tournament.matchUp.SaveMatchUpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class TournamentRepositoryImpl @Inject constructor(
    private val imageProcessor: ImageProcessor,
    private val mooditApi: MooditApi
) : TournamentRepository {
    override suspend fun uploadImage(photo: SelectedPhoto): Result<SelectedPhoto> {
        return withContext(Dispatchers.IO) {
            try {
                val filePart = imageProcessor.toMultiPartBody(photo.uri)
                    ?: throw IllegalArgumentException("파일 변환 실패")
                val response =
                    mooditApi.uploadFile(resourceType = PartType.MATCH.name, file = filePart)
                        .getOrThrow()
                val uploadedPhoto = photo.copy(
                    serverId = response.id,
                    status = UploadStatus.Success(response.fileUrl)
                )
                Result.Success(uploadedPhoto)
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

    override suspend fun saveMatchUp(matchId: Long, winnerId: Long, reasonId: Long): Result<Unit> {
        try {
            mooditApi.saveMatchUp(matchId, SaveMatchUpRequest(winnerId, reasonId)).getOrThrow()
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
}