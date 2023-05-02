package com.akmalmf.storyapp.ui.main.story_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.stories.StoriesResponse
import com.akmalmf.storyapp.domain.usecase.story.GetStoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 12:05.
 * akmalmf007@gmail.com
 */
@HiltViewModel
class StoryListViewModel @Inject constructor(private val storyListUsecase: GetStoryUseCase): ViewModel(){
    private val _stories = MutableLiveData<Resource<StoriesResponse>>()
    val stories: LiveData<Resource<StoriesResponse>> get() = _stories

    private fun getStories(){
        viewModelScope.launch {
            storyListUsecase.invoke().collect(){
                _stories.postValue(it)
            }
        }
    }

    init {
        getStories()
    }
}