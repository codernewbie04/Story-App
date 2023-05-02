package com.akmalmf.storyapp.domain.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 14:51.
 * akmalmf007@gmail.com
 */
inline fun <reified T> Gson.fromJson(json:String): T =
    this.fromJson(json,object : TypeToken<T>(){}.type)