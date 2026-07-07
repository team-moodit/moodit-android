package com.swyp.moodit.network.api

import com.swyp.moodit.model.tournament.CompletedTournamentDetail
import com.swyp.moodit.network.model.BaseResponse
import com.swyp.moodit.network.model.PagingResult
import com.swyp.moodit.network.model.mission.MissionAcceptRequest
import com.swyp.moodit.network.model.mission.MissionAcceptResponse
import com.swyp.moodit.network.model.mission.MissionCompleteResponse
import com.swyp.moodit.network.model.mission.MissionDetailResponse
import com.swyp.moodit.network.model.mission.MissionSatisfactionRequest
import com.swyp.moodit.network.model.report.ReportSummaryResponse
import com.swyp.moodit.network.model.tournament.CompletedMatchDetailResponse
import com.swyp.moodit.network.model.tournament.CreateMoodMatchRequest
import com.swyp.moodit.network.model.tournament.CreateMoodMatchResponse
import com.swyp.moodit.network.model.tournament.InProgressMatchDetailResponse
import com.swyp.moodit.network.model.tournament.MatchUpResultResponse
import com.swyp.moodit.network.model.tournament.MissionOfferRequest
import com.swyp.moodit.network.model.tournament.MissionOfferResponse
import com.swyp.moodit.network.model.tournament.PagingCompletedMatchResponse
import com.swyp.moodit.network.model.tournament.PagingInProgressMatchResponse
import com.swyp.moodit.network.model.tournament.PresignedUrlResponse
import com.swyp.moodit.network.model.tournament.UploadFileResponse
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpInitRequest
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpInitResponse
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpProgressResponse
import com.swyp.moodit.network.model.tournament.matchUp.SaveMatchUpRequest
import com.swyp.moodit.network.model.tournament.matchUp.SaveMatchUpResponse
import com.swyp.moodit.network.model.user.UserPrivacyInfoResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @GET("v1/files/presigned-url")
    suspend fun getPresignedUrl(
        @Query("resourceType") resourceType: String,
        @Query("fileName") fileName: String
    ): Response<BaseResponse<PresignedUrlResponse>>

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

    @GET("v1/matches/{matchId}/completed")
    suspend fun getMatchUpResult(
        @Path("matchId") matchId: Long
    ): Response<BaseResponse<MatchUpResultResponse>>

    @GET("v1/matches/moodtab/inprogress")
    suspend fun getPagingInProgressMoodMatches(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<BaseResponse<PagingResult<PagingInProgressMatchResponse>>>

    @GET("v1/matches/moodtab/completed")
    suspend fun getPagingCompletedMoodMatches(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Response<BaseResponse<PagingResult<PagingCompletedMatchResponse>>>

    @GET("v1/matches/{matchId}/progress")
    suspend fun getMatchUpProgressDetail(
        @Path("matchId") matchId: Long
    ): Response<BaseResponse<InProgressMatchDetailResponse>>

    @GET("v1/matches/{matchId}/result")
    suspend fun getMatchUpCompletedDetail(
        @Path("matchId") matchId: Long
    ): Response<BaseResponse<CompletedMatchDetailResponse>>

    // User
    @GET("v1/settings/privacy/info")
    suspend fun getUserPrivacyInfo(): Response<BaseResponse<UserPrivacyInfoResponse>>

    @POST("v1/settings/privacy/name")
    suspend fun postNickname(
        @Body request: String
    ): Response<BaseResponse<Unit>>

    // Mission
    @GET("v1/user-missions/{userMissionId}")
    suspend fun getMissionDetail(
        @Path("userMissionId") userMissionId: Long
    ): Response<BaseResponse<MissionDetailResponse>>

    @POST("v1/user-missions/{userMissionId}/complete")
    suspend fun completeMission(
        @Path("userMissionId") userMissionId: Long
    ): Response<BaseResponse<MissionCompleteResponse>>

    @POST("v1/reviews")
    suspend fun submitSatisfaction(
        @Body request: MissionSatisfactionRequest
    ): Response<BaseResponse<Unit>>

    @DELETE("v1/user-missions/{userMissionId}")
    suspend fun deleteMission(
        @Path("userMissionId") userMissionId: Long
    ): Response<BaseResponse<Unit>>

    @POST("v1/mission-offers")
    suspend fun getMissionOffers(
        @Body request: MissionOfferRequest
    ): Response<BaseResponse<MissionOfferResponse>>

    @POST("v1/mission-offers/accept")
    suspend fun acceptMissionOffer(
        @Body request: MissionAcceptRequest
    ): Response<BaseResponse<MissionAcceptResponse>>

    @GET("v1/user-missions")
    suspend fun getPagingMissions(
        @Query("state") type: String,
        @Query("offset") offset: Int,
        @Query("limit") size: Int
    ): Response<BaseResponse<PagingResult<MissionDetailResponse>>>

    // Report
    @GET("v1/reports")
    suspend fun getReportSummary(): Response<BaseResponse<ReportSummaryResponse>>
}
