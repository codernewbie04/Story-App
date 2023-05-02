package com.akmalmf.storyapp.domain.usecase.auth

import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.auth.RegisterResponse
import com.akmalmf.storyapp.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 14:15.
 * akmalmf007@gmail.com
 */
class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(name: String, email: String, password: String): Flow<Resource<RegisterResponse>> {
        return flow{
            emit(Resource.loading())
            emit(repository.register(name, email, password))
        }.flowOn(Dispatchers.IO)
    }
}