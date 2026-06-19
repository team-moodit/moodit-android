package com.swyp.moodit.model

import java.util.UUID

sealed interface UploadStatus {
    object Loading : UploadStatus
    data class Success(val serverUrl: String) : UploadStatus
    data class Error(val message: String) : UploadStatus
}

data class SelectedPhoto(
    val id: String = UUID.randomUUID().toString(),
    val serverId: Long? = null,
    val uri: String,
    val status: UploadStatus = UploadStatus.Loading
)