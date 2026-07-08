package com.swyp.moodit.model.tournament

data class CompletedTournamentDetail(
    val title: String = "",
    val winnerImage: TournamentImage = TournamentImage(),
    val imageUris: List<TournamentImage> = emptyList(),
    val preferenceResult: PreferenceResult = PreferenceResult(),
    val completedAt: String = "",

    )

data class PreferenceResult(
    val preferenceType: String = "",
    val preferenceDetailType: String = ""
)
