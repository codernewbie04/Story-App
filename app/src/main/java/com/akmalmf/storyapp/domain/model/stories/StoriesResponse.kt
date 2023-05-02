package com.akmalmf.storyapp.domain.model.stories

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 13:41.
 * akmalmf007@gmail.com
 */
data class StoriesResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("listStory")
    val listStory: List<Story>,
    @SerializedName("message")
    val message: String
)
