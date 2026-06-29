package com.swyp.moodit.network.api

import com.swyp.moodit.network.model.BaseResponse
import com.swyp.moodit.network.model.mission.MissionCompleteResponse
import com.swyp.moodit.network.model.mission.MissionDetailResponse
import com.swyp.moodit.network.model.tournament.CreateMoodMatchRequest
import com.swyp.moodit.network.model.tournament.CreateMoodMatchResponse
import com.swyp.moodit.network.model.tournament.UploadFileResponse
import com.swyp.moodit.network.model.user.UserProfileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
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

    @POST("v1/matches")
    suspend fun createMoodMatch(
        @Body request: CreateMoodMatchRequest
    ): Response<BaseResponse<CreateMoodMatchResponse>>

    // User
    @GET("v1/user-profiles/active")
    suspend fun getUserProfile(): Response<BaseResponse<UserProfileResponse>>

    // Mission
    @GET("v1/user-missions/{userMissionId}")
    suspend fun getMissionDetail(
        @Path("userMissionId") userMissionId: Long
    ): Response<BaseResponse<MissionDetailResponse>>

    @POST("v1/user-missions/{userMissionId}/complete")
    suspend fun completeMission(
        @Path("userMissionId") userMissionId: Long
    ): Response<BaseResponse<MissionCompleteResponse>>
}
