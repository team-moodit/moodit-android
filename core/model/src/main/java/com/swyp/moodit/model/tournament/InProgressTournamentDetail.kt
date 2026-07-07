package com.swyp.moodit.model.tournament

data class InProgressTournamentDetail(
    val title: String = "",
    val currentRound: Int = 0,
    val totalRound: Int = 0,
    val currentMatchOrder: Int = 0,
    val matchInfo: InProgressMatchInfo = InProgressMatchInfo(),
    val images: List<TournamentImage> = emptyList()
)

data class InProgressMatchInfo(
    val totalImageCount: Int = 0,
    val createdAt: String = ""
)
