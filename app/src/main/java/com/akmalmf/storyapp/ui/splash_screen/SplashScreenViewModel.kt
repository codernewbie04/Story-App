package com.akmalmf.storyapp.ui.splash_screen

import androidx.lifecycle.ViewModel
import com.akmalmf.storyapp.domain.repository.SharePrefRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 01/05/2023 19:18.
 * akmalmf007@gmail.com
 */
@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val sharedPref: SharePrefRepository
): ViewModel(){
  fun isLogedIn():Boolean = sharedPref.getAccessToken().isNotEmpty()
}