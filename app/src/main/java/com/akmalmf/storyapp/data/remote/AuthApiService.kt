package com.akmalmf.storyapp.data.remote

import com.akmalmf.storyapp.domain.model.auth.RegisterResponse
import com.akmalmf.storyapp.domain.model.auth.login.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 14:59.
 * akmalmf007@gmail.com
 */
interface AuthApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse
}