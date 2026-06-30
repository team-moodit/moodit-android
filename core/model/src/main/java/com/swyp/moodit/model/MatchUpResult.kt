package com.swyp.moodit.model

data class MatchUpResult(
    val matchResultId: Long,
    val winnerPhotoId: Long,
    val preferenceResultType: String,
    val mainPreference: String,
    val detailPreference: String
)
