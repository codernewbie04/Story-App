package com.akmalmf.storyapp.ui.main.story_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.stories.DetailStoryResponse
import com.akmalmf.storyapp.domain.usecase.story.DetailStoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 21:04.
 * akmalmf007@gmail.com
 */
@HiltViewModel
class StoryDetailViewModel @Inject constructor(private val detailStoryUseCase: DetailStoryUseCase): ViewModel(){
    fun detailStory(id: String): LiveData<Resource<DetailStoryResponse>> {
        return detailStoryUseCase.invoke(id).asLiveData(Dispatchers.Main)
    }
}