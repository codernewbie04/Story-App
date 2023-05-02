package com.akmalmf.storyapp.domain.utils

/**
 * Created by Akmal Muhamad Firdaus on 01/05/2023 12:23.
 * akmalmf007@gmail.com
 */
fun isValidEmail(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()