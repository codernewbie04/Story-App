package com.akmalmf.storyapp.ui.auth.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.auth.login.LoginResponse
import com.akmalmf.storyapp.domain.repository.SharePrefRepository
import com.akmalmf.storyapp.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 18:13.
 * akmalmf007@gmail.com
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sharePrefRepo: SharePrefRepository
) : ViewModel() {
    fun login(email: String, password: String): LiveData<Resource<LoginResponse>> {
        return loginUseCase(email, password).asLiveData(Dispatchers.Main)
    }

    fun setAccessToken(token: String) {
        sharePrefRepo.setAccessToken(token)
    }
}