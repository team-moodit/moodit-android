package com.swyp.moodit.data.mapper

import com.swyp.moodit.model.Candidate
import com.swyp.moodit.model.MatchUp
import com.swyp.moodit.model.MatchUpInfo
import com.swyp.moodit.model.MatchUpReason
import com.swyp.moodit.network.model.tournament.matchUp.CandidateResponse
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpInitResponse
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpReasonResponse
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpResponse

fun MatchUpInitResponse.toModel(): MatchUpInfo {
    return MatchUpInfo(
        title = this.tournamentTitle,
        totalRounds = this.totalRounds,
        currentRound = this.currentRound,
        roundTitle = this.roundName,
        isCompleted = this.isTournamentCompleted,
        nextMatchUp = this.nextMatchUp?.toModel(),
        reasons = this.reasons.map { it.toModel() }
    )
}

fun MatchUpResponse.toModel(): MatchUp {
    return MatchUp(
        candidateA = this.candidateA.toModel(),
        candidateB = this.candidateB.toModel()
    )
}

fun CandidateResponse.toModel(): Candidate {
    return Candidate(
        id = this.id,
        photoUri = this.photoUri
    )
}

fun MatchUpReasonResponse.toModel(): MatchUpReason {
    return MatchUpReason(
        id = this.id,
        content = this.content
    )
}