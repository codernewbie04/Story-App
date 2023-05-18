package com.akmalmf.storyapp.di

import com.akmalmf.storyapp.data.remote.AuthApiService
import com.akmalmf.storyapp.data.remote.AuthInterceptor
import com.akmalmf.storyapp.data.remote.StoryApiService
import com.akmalmf.storyapp.di.qualifiers.AuthenticatedRetrofitClient
import com.akmalmf.storyapp.di.qualifiers.NotAuthenticatedRetrofitClient
import com.akmalmf.storyapp.domain.repository.SharePrefRepository
import com.facebook.shimmer.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
    companion object {

        const val BASE_URL = "https://story-api.dicoding.dev/v1/"
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor{
        val logging = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
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
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @AuthenticatedRetrofitClient
    fun provideRetrofitClientAuthenticate(@AuthenticatedRetrofitClient client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
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