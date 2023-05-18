package com.akmalmf.storyapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.akmalmf.storyapp.data.abstraction.RemoteDataSource
import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.data.remote.StoryApiService
import com.akmalmf.storyapp.domain.model.stories.AddStoryResponse
import com.akmalmf.storyapp.domain.model.stories.DetailStoryResponse
import com.akmalmf.storyapp.domain.model.stories.StoriesResponse
import com.akmalmf.storyapp.domain.model.stories.StoryResponse
import com.akmalmf.storyapp.domain.repository.StoryRepository
import com.google.android.gms.maps.model.LatLng
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 11:06.
 * akmalmf007@gmail.com
 */
class StoryRepositoryImpl @Inject constructor(
    private val api: StoryApiService
) : StoryRepository, RemoteDataSource() {
    override suspend fun createStory(
        description: String,
        photo: MultipartBody.Part,
        location: LatLng?
    ): Resource<AddStoryResponse> {
        return safeApiCall {
            if (location != null) {
                api.createStory(
                    description.toRequestBody("text/plain".toMediaType()),
                    location.latitude.toString().toRequestBody("text/plain".toMediaType()),
                    location.longitude.toString().toRequestBody("text/plain".toMediaType()),
                    photo
                )
            } else {
                api.createStory(
                    description.toRequestBody("text/plain".toMediaType()),
                    null,
                    null,
                    photo
                )
            }
        }
    }

    override fun getStories(): LiveData<PagingData<StoryResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingDataSource(api)
            }
        ).liveData
    }

    override suspend fun detailStory(id: String): Resource<DetailStoryResponse> {
        return safeApiCall {
            api.detailStory(id)
        }
    }

    override suspend fun getStoriesMap(): Resource<StoriesResponse> {
        return safeApiCall {
            api.getStories(1, 1, 30)
        }
    }
}