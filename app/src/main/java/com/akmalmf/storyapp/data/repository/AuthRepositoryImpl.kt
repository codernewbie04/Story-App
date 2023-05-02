package com.akmalmf.storyapp.data.repository

import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.data.remote.AuthApiService
import com.akmalmf.storyapp.data.abstraction.RemoteDataSource
import com.akmalmf.storyapp.domain.model.auth.RegisterResponse
import com.akmalmf.storyapp.domain.model.auth.login.LoginResponse
import com.akmalmf.storyapp.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 14:57.
 * akmalmf007@gmail.com
 */
class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService
) : AuthRepository, RemoteDataSource() {
    override suspend fun login(email: String, password: String): Resource<LoginResponse> {
        return safeApiCall{
            api.login(email, password)
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Resource<RegisterResponse> {
        return safeApiCall{
            api.register(name, email, password)
        }
    }
}