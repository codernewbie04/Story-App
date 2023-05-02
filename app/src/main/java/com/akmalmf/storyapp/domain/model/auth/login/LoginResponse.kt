package com.akmalmf.storyapp.domain.model.auth.login

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 11:23.
 * akmalmf007@gmail.com
 */
data class LoginResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("loginResult")
    val loginResult: LoginResult,
    @SerializedName("message")
    val message: String
)
