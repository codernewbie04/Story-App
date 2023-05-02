package com.akmalmf.storyapp.domain.model.stories

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 11:03.
 * akmalmf007@gmail.com
 */
data class DetailStoryResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("story")
    val story: StoryResponse
)