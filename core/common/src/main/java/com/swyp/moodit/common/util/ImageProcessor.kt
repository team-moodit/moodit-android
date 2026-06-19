package com.swyp.moodit.common.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.io.path.createTempFile

@Singleton
class ImageProcessor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @SuppressLint("Recycle")
    suspend fun uriToFile(uri: String): File? = withContext(Dispatchers.IO) {
        try {
            val uri = uri.toUri()
            val tempFile =
                createTempFile(directory = context.cacheDir.toPath(), "photo_", ".png").toFile()
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(tempFile)
            inputStream.use { input ->
                outputStream.use { output ->
                    input?.copyTo(output)
                }
            }
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun toMultiPartBody(uri: String, partName: String = "file"): MultipartBody.Part? {
        val file = uriToFile(uri) ?: return null
        val requestFile = file.asRequestBody(contentType = "image/png".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }
}
