package com.akmalmf.storyapp.di

import android.content.Context
import com.akmalmf.storyapp.data.remote.AuthApiService
import com.akmalmf.storyapp.data.remote.StoryApiService
import com.akmalmf.storyapp.data.repository.AuthRepositoryImpl
import com.akmalmf.storyapp.data.repository.SharePrefRepositoryImpl
import com.akmalmf.storyapp.data.repository.StoryRepositoryImpl
import com.akmalmf.storyapp.domain.repository.AuthRepository
import com.akmalmf.storyapp.domain.repository.SharePrefRepository
import com.akmalmf.storyapp.domain.repository.StoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 15:55.
 * akmalmf007@gmail.com
 */
@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesAuthRepository(api: AuthApiService): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesStoryRepository(api: StoryApiService): StoryRepository {
        return  StoryRepositoryImpl(api)
    }


    @Singleton
    @Provides
    fun provideSharePrefRepository(@ApplicationContext context: Context): SharePrefRepository {
        return SharePrefRepositoryImpl(context)
    }




}