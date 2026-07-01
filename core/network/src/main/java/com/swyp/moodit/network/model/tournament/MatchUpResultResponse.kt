package com.swyp.moodit.network.model.tournament

data class MatchUpResultResponse(
    val matchResultId: Long,
    val winnerPhotoId: Long,
    val preferenceResultType: String,
    val mainPreference: String?,
    val detailPreference: String?
)
