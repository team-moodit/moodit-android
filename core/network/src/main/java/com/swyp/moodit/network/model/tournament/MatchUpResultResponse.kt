package com.swyp.moodit.network.model.tournament

import com.google.gson.annotations.SerializedName

data class MatchUpResultResponse(
    @SerializedName("matchUpWinnerId")
    val matchResultId: Long,
    val winnerPhotoId: Long,
    val preferenceResultType: String,
    val mainPreference: String?,
    val detailPreference: String?
)
