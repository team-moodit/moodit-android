package com.swyp.moodit.data.mapper

import com.swyp.moodit.model.Candidate
import com.swyp.moodit.model.MatchUp
import com.swyp.moodit.model.MatchUpInfo
import com.swyp.moodit.model.MatchUpReason
import com.swyp.moodit.model.MatchUpResult
import com.swyp.moodit.model.MissionMatchResult
import com.swyp.moodit.model.MissionSuggestion
import com.swyp.moodit.model.MoodMatchResult
import com.swyp.moodit.model.PreferenceResultType
import com.swyp.moodit.model.SelectedMatchUpIds
import com.swyp.moodit.network.model.tournament.MatchUpResultResponse
import com.swyp.moodit.network.model.tournament.MissionMatchResultResponse
import com.swyp.moodit.network.model.tournament.MissionOfferResponse
import com.swyp.moodit.network.model.tournament.MissionSuggestionResponse
import com.swyp.moodit.network.model.tournament.matchUp.CandidateResponse
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpInitResponse
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpProgressResponse
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpReasonResponse
import com.swyp.moodit.network.model.tournament.matchUp.MatchUpResponse
import com.swyp.moodit.network.model.tournament.matchUp.SaveMatchUpRequest

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

fun MatchUpProgressResponse.toModel(): MatchUpInfo {
    return MatchUpInfo(
        title = this.tournamentTitle,
        roundTitle = this.roundTitle,
        curMatchIndex = this.currentMatchIndex,
        totalRounds = this.totalMatchUpInRound,
        isCompleted = this.isTournamentCompleted,
        nextMatchUp = this.nextMatchUp?.toModel(),
        reasons = this.reasons.map { it.toModel() },
    )
}

fun MatchUpResponse.toModel(): MatchUp {
    return MatchUp(
        matchUpId = this.matchUpId,
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

fun MatchUpResultResponse.toModel(): MatchUpResult {
    return MatchUpResult(
        matchResultId = this.matchResultId,
        winnerPhotoId = this.winnerPhotoId,
        preferenceResultType = this.preferenceResultType.toModel(),
        mainPreference = this.mainPreference ?: "",
        detailPreference = this.detailPreference ?: ""
    )
}

fun MissionOfferResponse.toModel(): MoodMatchResult {
    return MoodMatchResult(
        offerId = this.offerId,
        preferenceResultType = this.preferenceResultType.toModel(),
        missionSuggestions = this.items.map { it.toModel() },
        state = this.state,
        assignedMissionId = this.assignedMissionId ?: 0L,
        matchResult = this.matchResult.toModel()
    )
}

fun SelectedMatchUpIds.toNetworkRequest(): SaveMatchUpRequest {
    return SaveMatchUpRequest(
        photoId = this.winnerId,
        reasonId = this.reasonId,
        matchUpId = this.matchUpId
    )
}

fun MissionSuggestionResponse.toModel(): MissionSuggestion {
    return MissionSuggestion(
        id = this.id,
        title = this.title
    )
}

fun MissionMatchResultResponse.toModel(): MissionMatchResult {
    return MissionMatchResult(
        matchResultId = this.matchResultId,
        matchTitle = this.matchTitle,
        imageUrl = this.matchRepresentativeImageUrl,
        matchPreferenceTypeTitle = this.matchPreferenceTypeTitle,
        matchRoundCount = this.matchRoundCount,
        matchCompletedAt = this.matchCompletedAt
    )
}

fun String.toModel(): PreferenceResultType {
    return PreferenceResultType.entries.find { it.name == this } ?: PreferenceResultType.TIE
}