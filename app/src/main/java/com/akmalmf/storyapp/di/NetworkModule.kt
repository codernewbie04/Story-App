package com.akmalmf.storyapp.di

import android.util.Log
import com.akmalmf.storyapp.data.remote.AuthApiService
import com.akmalmf.storyapp.data.remote.AuthInterceptor
import com.akmalmf.storyapp.data.remote.StoryApiService
import com.akmalmf.storyapp.di.qualifiers.AuthenticatedRetrofitClient
import com.akmalmf.storyapp.di.qualifiers.NotAuthenticatedRetrofitClient
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
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }



    @Singleton
    @Provides
    @NotAuthenticatedRetrofitClient
    fun providesOkHttpClientNotAuthenticate(httpLoggingInterceptor: HttpLoggingInterceptor)= OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Singleton
    @Provides
    @AuthenticatedRetrofitClient
    fun providesOkHttpClientAuthenticate(httpLoggingInterceptor: HttpLoggingInterceptor, sharePrefRepository: SharePrefRepository): OkHttpClient {
        Log.i("give_api", "Provide API!")
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
    @NotAuthenticatedRetrofitClient
    fun provideRetrofitClientNotAuthenticate(@NotAuthenticatedRetrofitClient client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @AuthenticatedRetrofitClient
    fun provideRetrofitClientAuthenticate(@AuthenticatedRetrofitClient client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthApiService(@NotAuthenticatedRetrofitClient retrofit: Retrofit): AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Singleton
    @Provides
    fun provideStoryApiService(@AuthenticatedRetrofitClient retrofit: Retrofit): StoryApiService =
        retrofit.create(StoryApiService::class.java)
}