package com.swyp.moodit.data.impl

import com.swyp.moodit.common.util.ImageProcessor
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.data.repository.TournamentRepository
import com.swyp.moodit.model.PartType
import com.swyp.moodit.model.SelectedPhoto
import com.swyp.moodit.model.UploadStatus
import com.swyp.moodit.network.api.MooditApi
import com.swyp.moodit.network.model.getOrThrow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
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
                val response = mooditApi.uploadFile(resourceType = PartType.MATCH.name, file = filePart).getOrThrow()
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
}