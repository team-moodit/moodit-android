package com.swyp.moodit.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.swyp.moodit.data.mapper.toModel
import com.swyp.moodit.model.tournament.InProgressTournament
import com.swyp.moodit.network.api.MooditApi
import com.swyp.moodit.network.model.getOrThrow

class InProgressTournamentPagingSource(
    private val mooditApi: MooditApi
) : PagingSource<Int, InProgressTournament>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, InProgressTournament> {
        val currentPage = params.key ?: 0
        return try {
            val response =
                mooditApi.getPagingInProgressMoodMatches(page = currentPage, size = params.loadSize)
                    .getOrThrow()
            val tournaments =
                response.content.flatMap { matchResponse -> matchResponse.content.map { it.toModel() } }
            val hasNext = response.hasNext
            LoadResult.Page(
                data = tournaments,
                prevKey = if (currentPage > 0) currentPage - 1 else null,
                nextKey = if (hasNext && tournaments.isNotEmpty()) currentPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, InProgressTournament>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}