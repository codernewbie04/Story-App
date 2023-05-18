package com.akmalmf.storyapp.fakerepo

import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.data.abstraction.Status
import com.akmalmf.storyapp.domain.model.auth.RegisterResponse
import com.akmalmf.storyapp.domain.model.auth.login.LoginResponse
import com.akmalmf.storyapp.domain.model.auth.login.LoginResult
import com.akmalmf.storyapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow

/**
 * Created by Akmal Muhamad Firdaus on 10/05/2023 18:10.
 * akmalmf007@gmail.com
 */
class FakeAuthRepository: AuthRepository {
    override suspend fun login(email: String, password: String): Resource<LoginResponse> {
        val loginResult = LoginResult("Akmal Muhamad Firdaus","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXNzcnp0ek9BeWphVGZWU3oiLCJpYXQiOjE2ODM1NTg3NzV9.KTQCWx6a33dSvywa8L5ABItVqZV6FtweCiLrBXRv-uA","user-ssrztzOAyjaTfVSz")
        val loginResponse = LoginResponse(true, loginResult, "oke!")
        return Resource<LoginResponse>(status= Status.SUCCESS, data = loginResponse, code = 200, message = "ok")
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Resource<RegisterResponse> {
        TODO("Not yet implemented")
    }
}