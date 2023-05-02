package com.akmalmf.storyapp.domain.repository

/**
 * Created by Akmal Muhamad Firdaus on 01/05/2023 13:24.
 * akmalmf007@gmail.com
 */
interface SharePrefRepository {
    fun getAccessToken(): String
    fun setAccessToken(value: String)
}