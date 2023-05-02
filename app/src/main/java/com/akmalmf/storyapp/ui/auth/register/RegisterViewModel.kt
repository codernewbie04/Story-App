package com.akmalmf.storyapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.auth.RegisterResponse
import com.akmalmf.storyapp.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 01/05/2023 13:56.
 * akmalmf007@gmail.com
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {
    private val _registerLiveData = MutableLiveData<Resource<RegisterResponse>>()

    fun register(name: String, email: String, password: String): LiveData<Resource<RegisterResponse>> {
        return registerUseCase(name, email, password).asLiveData(Dispatchers.Main)
    }
}