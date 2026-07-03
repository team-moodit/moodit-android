package com.swyp.moodit.network.api

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Url

interface S3Api {
    @PUT
    suspend fun uploadImageToS3(
        @Url url: String,
        @Header("Content-Type") contentType: String = "image/jpeg",
        @Body file: RequestBody
    ): Response<Unit>
}