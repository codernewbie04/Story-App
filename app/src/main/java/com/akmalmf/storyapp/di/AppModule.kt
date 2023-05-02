package com.akmalmf.storyapp.di

import android.content.Context
import android.util.Log
import com.akmalmf.storyapp.data.remote.AuthApiService
import com.akmalmf.storyapp.data.repository.AuthRepositoryImpl
import com.akmalmf.storyapp.data.repository.SharePrefRepositoryImpl
import com.akmalmf.storyapp.domain.repository.AuthRepository
import com.akmalmf.storyapp.domain.repository.SharePrefRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 15:55.
 * akmalmf007@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAPI(): AuthApiService{
        Log.i("Provide_data", "Provide API!")
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(AuthApiService::class.java)

    }

    @Provides
    @Singleton
    fun providesAuthRepository(api: AuthApiService): AuthRepository{
        return AuthRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideSharePrefRepository(@ApplicationContext context: Context): SharePrefRepository {
        return SharePrefRepositoryImpl(context)
    }
}