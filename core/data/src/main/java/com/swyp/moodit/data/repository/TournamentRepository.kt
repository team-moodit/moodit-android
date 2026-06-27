package com.swyp.moodit.data.repository

import com.swyp.moodit.common.util.Result
import com.swyp.moodit.model.MatchUpInfo
import com.swyp.moodit.model.SelectedPhoto

interface TournamentRepository {
    suspend fun uploadImage(photo: SelectedPhoto): Result<SelectedPhoto>
    suspend fun createMoodMatch(title: String, imageIds: List<Long>): Result<Long>
    suspend fun getMatchUpInitInfo(matchId: Long): Result<MatchUpInfo>
    suspend fun saveMatchUp(matchId: Long, winnerId: Long, reasonId: Long): Result<Unit>
}