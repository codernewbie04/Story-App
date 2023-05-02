package com.akmalmf.storyapp.di

import android.util.Log
import com.akmalmf.storyapp.data.remote.AuthApiService
import com.akmalmf.storyapp.data.remote.AuthInterceptor
import com.akmalmf.storyapp.domain.repository.SharePrefRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 09:27.
 * akmalmf007@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
//    @Provides
//    @Singleton
//    fun provideAPI(): AuthApiService {
//        Log.i("give_api", "Provide API!")
//        val logging = HttpLoggingInterceptor()
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//        val client: OkHttpClient = OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://story-api.dicoding.dev/v1/")
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        return retrofit.create(AuthApiService::class.java)
//
//    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }



    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, sharePrefRepository: SharePrefRepository): OkHttpClient {
        val token = sharePrefRepository.getAccessToken()
        return if (token.isEmpty()){
            OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
        } else {
            OkHttpClient
                .Builder()
                .addInterceptor(AuthInterceptor(token))
                .addInterceptor(httpLoggingInterceptor)
                .build()
        }
    }


    @Provides
    @Singleton
    fun provideRetrofitClient(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)
}