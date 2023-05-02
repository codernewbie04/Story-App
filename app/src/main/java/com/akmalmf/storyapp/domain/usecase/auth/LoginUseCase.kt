package com.akmalmf.storyapp.domain.usecase.auth

import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.auth.login.LoginResponse
import com.akmalmf.storyapp.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 14:15.
 * akmalmf007@gmail.com
 */
class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<LoginResponse>> {
        return flow{
            emit(Resource.loading())
            emit(repository.login(email, password))
        }.flowOn(Dispatchers.IO)
    }
}