package com.swyp.moodit.model.tournament

data class CompletedTournament(
    val userMissionId: Long = -1L,
    val matchId: Long = -1L,
    val title: String = "",
    val winnerImageId: Long = -1L,
    val winnerImageUri: String = ""
)
