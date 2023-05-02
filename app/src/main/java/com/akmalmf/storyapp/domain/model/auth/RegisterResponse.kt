package com.akmalmf.storyapp.domain.model.auth

import com.google.gson.annotations.SerializedName


/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 11:25.
 * akmalmf007@gmail.com
 */
data class RegisterResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)
