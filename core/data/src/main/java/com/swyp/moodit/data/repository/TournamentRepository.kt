package com.swyp.moodit.data.repository

import androidx.paging.PagingData
import com.swyp.moodit.common.util.Result
import com.swyp.moodit.model.MatchUpInfo
import com.swyp.moodit.model.MatchUpResult
import com.swyp.moodit.model.SelectedMatchUpIds
import com.swyp.moodit.model.SelectedPhoto
import com.swyp.moodit.model.tournament.CompletedTournamentDetail
import com.swyp.moodit.model.tournament.InProgressTournament
import com.swyp.moodit.model.tournament.InProgressTournamentDetail
import kotlinx.coroutines.flow.Flow

interface TournamentRepository {
    val onGoingTournamentId: Flow<Long>
    fun getPagingInProgressTournaments(): Flow<PagingData<InProgressTournament>>
    suspend fun uploadImage(photo: SelectedPhoto): Result<SelectedPhoto>
    suspend fun createMoodMatch(title: String, imageIds: List<Long>): Result<Long>
    suspend fun getMatchUpInitInfo(matchId: Long): Result<MatchUpInfo>
    suspend fun saveMatchUp(matchId: Long, selectedMatchUpIds: SelectedMatchUpIds): Result<Unit>
    suspend fun getMatchUpProgressInfo(matchId: Long): Result<MatchUpInfo>
    suspend fun getMatchUpResult(matchId: Long): Result<MatchUpResult>
    suspend fun setOnGoingTournamentId(tournamentId: Long): Result<Unit>
    suspend fun clearOnGoingTournamentId(): Result<Unit>
    suspend fun getInProgressTournamentDetail(matchId: Long): Result<InProgressTournamentDetail>
    suspend fun getCompletedTournamentDetail(matchId: Long): Result<CompletedTournamentDetail>
}