package com.akmalmf.storyapp.domain.model.stories

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 11:28.
 * akmalmf007@gmail.com
 */
data class AddStoryResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)
