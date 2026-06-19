package com.swyp.moodit.data.repository

import com.swyp.moodit.common.util.Result
import com.swyp.moodit.model.SelectedPhoto

interface TournamentRepository {
    suspend fun uploadImage(photo: SelectedPhoto): Result<SelectedPhoto>
}