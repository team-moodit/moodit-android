package com.swyp.moodit.model

data class MatchUpInfo(
    val title: String = "",
    val roundTitle: String = "",
    val totalRounds: Int = 0,
    val currentRound: Int = 0,
    val curMatchIndex: Int = 1,
    val isCompleted: Boolean = false,
    val nextMatchUp: MatchUp? = null,
    val reasons: List<MatchUpReason> = emptyList()
)
