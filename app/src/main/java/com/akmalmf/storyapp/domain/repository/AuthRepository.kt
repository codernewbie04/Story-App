package com.akmalmf.storyapp.domain.repository

import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.auth.RegisterResponse
import com.akmalmf.storyapp.domain.model.auth.login.LoginResponse

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 13:44.
 * akmalmf007@gmail.com
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<LoginResponse>
    suspend fun register(name: String, email: String, password: String): Resource<RegisterResponse>
}