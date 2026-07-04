package com.swyp.moodit.model.tournament

data class InProgressTournamentDetail(
    val id: Long = 0L,
    val title: String = "",
    val currentRound: String = "",
    val totalRound: String = "",
    val lastProgressedAt: String = "",
    val imageUris: List<String> = emptyList()
)
