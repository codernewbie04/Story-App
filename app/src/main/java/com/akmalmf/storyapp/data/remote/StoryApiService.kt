package com.akmalmf.storyapp.data.remote

import com.akmalmf.storyapp.domain.model.stories.AddStoryResponse
import com.akmalmf.storyapp.domain.model.stories.DetailStoryResponse
import com.akmalmf.storyapp.domain.model.stories.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Akmal Muhamad Firdaus on 01/05/2023 19:54.
 * akmalmf007@gmail.com
 */
interface StoryApiService {
    //Next update: Implement pagination
    @GET("stories")
    suspend fun getStories(): StoriesResponse

    @GET("stories/{id}")
    suspend fun detailStory(@Path("id") id: String): DetailStoryResponse

    @Multipart
    @POST("stories")
    suspend fun createStory(
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part,
    ): AddStoryResponse

}