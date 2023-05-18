package com.akmalmf.storyapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.akmalmf.storyapp.data.remote.StoryApiService
import com.akmalmf.storyapp.domain.model.stories.StoryResponse
import com.akmalmf.storyapp.domain.repository.StoryRepository
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/05/18 04:55
 * akmalmf007@gmail.com
 */
class StoryPagingDataSource @Inject constructor(
    private val api: StoryApiService
) : PagingSource<Int, StoryResponse>() {

    override fun getRefreshKey(state: PagingState<Int, StoryResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryResponse> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = api.getStories(0, page, params.loadSize)
            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.listStory.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}