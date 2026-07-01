package com.swyp.moodit.model

data class MatchUpResult(
    val matchResultId: Long,
    val winnerPhotoId: Long,
    val preferenceResultType: PreferenceResultType,
    val mainPreference: String,
    val detailPreference: String
)

enum class PreferenceResultType{
    TYPE_ONLY,
    TYPE_AND_DETAIL,
    TIE
}
