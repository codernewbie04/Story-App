package com.akmalmf.storyapp.ui.splash_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.akmalmf.storyapp.R
import com.akmalmf.storyapp.ui.auth.AuthActivity
import com.akmalmf.storyapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private val viewModel: SplashScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        if(viewModel.isLogedIn()){
            MainActivity.start(this)
        } else {
            AuthActivity.start(this)
        }
    }
}