package com.swyp.moodit.network.model.tournament.matchUp

data class MatchUpResponse(
    val candidateA: CandidateResponse,
    val candidateB: CandidateResponse
)
