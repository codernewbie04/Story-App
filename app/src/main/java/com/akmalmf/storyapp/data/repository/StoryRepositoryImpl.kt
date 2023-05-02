package com.akmalmf.storyapp.data.repository

import com.akmalmf.storyapp.data.abstraction.RemoteDataSource
import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.data.remote.StoryApiService
import com.akmalmf.storyapp.domain.model.stories.AddStoryResponse
import com.akmalmf.storyapp.domain.model.stories.DetailStoryResponse
import com.akmalmf.storyapp.domain.model.stories.StoriesResponse
import com.akmalmf.storyapp.domain.repository.StoryRepository
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
): StoryRepository, RemoteDataSource(){
    override suspend fun createStory(
        description: String,
        photo: MultipartBody.Part
    ): Resource<AddStoryResponse> {
        return safeApiCall {
            api.createStory(description.toRequestBody("text/plain".toMediaType()), photo)
        }
    }

    override suspend fun getStories(): Resource<StoriesResponse> {
        return safeApiCall{
            api.getStories()
        }
    }

    override suspend fun detailStory(id: String): Resource<DetailStoryResponse> {
        return safeApiCall {
            api.detailStory(id)
        }
    }
}