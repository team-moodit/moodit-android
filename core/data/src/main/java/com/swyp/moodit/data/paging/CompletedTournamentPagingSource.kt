package com.swyp.moodit.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.swyp.moodit.data.mapper.toModel
import com.swyp.moodit.model.tournament.CompletedTournament
import com.swyp.moodit.network.api.MooditApi
import com.swyp.moodit.network.model.getOrThrow

class CompletedTournamentPagingSource(
    private val mooditApi: MooditApi
) : PagingSource<Int, CompletedTournament>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CompletedTournament> {
        val currentPage = params.key ?: 0
        return try {
            val response =
                mooditApi.getPagingCompletedMoodMatches(page = currentPage, size = params.loadSize)
                    .getOrThrow()
            val tournaments = response.content.map { it.toModel() }
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

    override fun getRefreshKey(state: PagingState<Int, CompletedTournament>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}