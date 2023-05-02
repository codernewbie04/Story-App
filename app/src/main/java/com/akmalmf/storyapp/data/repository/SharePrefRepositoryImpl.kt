package com.akmalmf.storyapp.data.repository

import android.content.Context
import com.akmalmf.storyapp.data.constan.SharePrefKey
import com.akmalmf.storyapp.domain.repository.SharePrefRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 01/05/2023 13:25.
 * akmalmf007@gmail.com
 */
class SharePrefRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
): SharePrefRepository{
    private val sharePref = context.getSharedPreferences(SharePrefKey.MY_APP_KEY, Context.MODE_PRIVATE)

    override fun getAccessToken(): String = sharePref.getString(SharePrefKey.ACCESS_TOKEN, "") ?: ""

    override fun setAccessToken(value: String) {
        sharePref.edit().putString(SharePrefKey.ACCESS_TOKEN, value).apply()
    }

}