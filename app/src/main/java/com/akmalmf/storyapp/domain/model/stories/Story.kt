package com.akmalmf.storyapp.domain.model.stories

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 13:42.
 * akmalmf007@gmail.com
 */
data class Story(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("photoUrl")
    val photoUrl: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)
