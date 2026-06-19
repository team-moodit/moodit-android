package com.swyp.moodit.network.api

import com.swyp.moodit.network.model.BaseResponse
import com.swyp.moodit.network.model.tournament.UploadFileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface MooditApi {

    @POST("v1/auth/logout")
    suspend fun logout(): Response<BaseResponse<Unit>>

    // Tournament
    @Multipart
    @POST("v1/files/upload")
    suspend fun uploadFile(
        @Query("resourceType") resourceType: String,
        @Part file: MultipartBody.Part
    ): Response<BaseResponse<UploadFileResponse>>
}
