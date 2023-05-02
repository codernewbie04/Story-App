package com.akmalmf.storyapp.ui.main.story_create

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.stories.AddStoryResponse
import com.akmalmf.storyapp.domain.usecase.story.CreateStoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 17:37.
 * akmalmf007@gmail.com
 */
@HiltViewModel
class StoryCreateViewModel @Inject constructor(
    private val createStory: CreateStoryUseCase
): ViewModel()  {
    fun createStory(description: String, photo: MultipartBody.Part): LiveData<Resource<AddStoryResponse>> {
        return createStory.invoke(description, photo).asLiveData(Dispatchers.Main)
    }
}