package com.akmalmf.storyapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.stories.AddStoryResponse
import com.akmalmf.storyapp.domain.model.stories.DetailStoryResponse
import com.akmalmf.storyapp.domain.model.stories.StoriesResponse
import com.akmalmf.storyapp.domain.model.stories.StoryResponse
import com.google.android.gms.maps.model.LatLng
import okhttp3.MultipartBody

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 10:56.
 * akmalmf007@gmail.com
 */
interface StoryRepository {
    suspend fun createStory(description: String, photo: MultipartBody.Part, location: LatLng?): Resource<AddStoryResponse>
    fun getStories(): LiveData<PagingData<StoryResponse>>
    suspend fun detailStory(id: String): Resource<DetailStoryResponse>

    suspend fun getStoriesMap(): Resource<StoriesResponse>
}