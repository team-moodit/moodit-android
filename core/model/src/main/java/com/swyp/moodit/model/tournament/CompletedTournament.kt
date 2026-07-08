package com.swyp.moodit.model.tournament

data class CompletedTournament(
    val matchId: Long = -1L,
    val title: String = "",
    val winnerImageId: Long = -1L,
    val winnerImageUri: String = ""
)
