package com.akmalmf.storyapp.domain.model.auth.login

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 11:22.
 * akmalmf007@gmail.com
 */
data class LoginResult(
    @SerializedName("name")
    val name: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("userId")
    val userId: String
)
