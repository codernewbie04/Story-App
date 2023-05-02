package com.akmalmf.storyapp.domain.repository

import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.stories.AddStoryResponse
import com.akmalmf.storyapp.domain.model.stories.DetailStoryResponse
import com.akmalmf.storyapp.domain.model.stories.StoriesResponse
import okhttp3.MultipartBody

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 10:56.
 * akmalmf007@gmail.com
 */
interface StoryRepository {
    suspend fun createStory(description: String, photo: MultipartBody.Part): Resource<AddStoryResponse>
    suspend fun getStories(): Resource<StoriesResponse>
    suspend fun detailStory(id: String): Resource<DetailStoryResponse>
}