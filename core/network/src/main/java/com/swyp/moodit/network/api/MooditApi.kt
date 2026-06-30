package com.swyp.moodit.network.api

import com.swyp.moodit.network.model.BaseResponse
import com.swyp.moodit.network.model.tournament.CreateMoodMatchRequest
import com.swyp.moodit.network.model.tournament.CreateMoodMatchResponse
import com.swyp.moodit.network.model.tournament.MatchUpResultResponse
import com.swyp.moodit.network.model.tournament.UploadFileResponse
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpInitRequest
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpInitResponse
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpProgressResponse
import com.swyp.moodit.network.model.tournament.matchUp.SaveMatchUpRequest
import com.swyp.moodit.network.model.tournament.matchUp.SaveMatchUpResponse
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

    @GET("v1/matches/{matchId}/start")
    suspend fun getMatchUpInitInfo(
        @Path("matchId") request: MatchUpInitRequest
    ): Response<BaseResponse<MatchUpInitResponse>>

    @POST("v1/matches/{matchId}/votes")
    suspend fun saveMatchUp(
        @Path("matchId") matchId: Long,
        @Body request: SaveMatchUpRequest
    ): Response<BaseResponse<SaveMatchUpResponse>>

    @GET("v1/matches/{matchId}/next-matchup")
    suspend fun getNextMatchUpInfo(
        @Path("matchId") matchId: Long
    ): Response<BaseResponse<MatchUpProgressResponse>>

    @GET("v1/matches/{matchId}/complete")
    suspend fun getMatchUpResult(
        @Path("matchId") matchId: Long
    ): Response<BaseResponse<MatchUpResultResponse>>

    // User
    @GET("v1/user-profiles/active")
    suspend fun getUserProfile(): Response<BaseResponse<UserProfileResponse>>
}
