package com.swyp.moodit.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.swyp.moodit.data.mapper.toModel
import com.swyp.moodit.model.Mission
import com.swyp.moodit.network.api.MooditApi
import com.swyp.moodit.network.model.getOrThrow

class MissionPagingSource(
    private val mooditApi: MooditApi,
    private val type: String
) : PagingSource<Int, Mission>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Mission> {
        val currentPage = params.key ?: 0
        val pageSize = params.loadSize
        val offset = currentPage * pageSize

        return try {
            val response =
                mooditApi.getPagingMissions(type = type, offset = offset, size = pageSize)
                    .getOrThrow().content.map { it.toModel() }
            
            LoadResult.Page(
                data = response,
                prevKey = if (currentPage > 0) currentPage - 1 else null,
                nextKey = if (response.size < pageSize) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Mission>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}