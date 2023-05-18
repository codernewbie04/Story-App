package com.akmalmf.storyapp.data.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 10:43.
 * akmalmf007@gmail.com
 */
class   AuthInterceptor(private var token: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        request = if (request.header("No-Authentication") == null && token.isNotEmpty()) {
            val finalToken = "Bearer $token"
            request.newBuilder()
                .addHeader("Authorization", finalToken)
                .build()
        } else {
            request.newBuilder()
                .build()
        }
        return chain.proceed(request)
    }
}