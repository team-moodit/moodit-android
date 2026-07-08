package com.swyp.moodit.model.tournament

data class InProgressTournament(
    val matchId: Long = -1L,
    val title: String = "",
    val currentRound: Int = 0,
    val totalRound: Int = 0,
    val lastPlayedAt: String = ""
)
