package com.swyp.moodit.model

data class MatchUp(
    val matchUpId: Long,
    val candidateA: Candidate,
    val candidateB: Candidate
)
