package com.akmalmf.storyapp.ui.main.story_map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.stories.StoriesResponse
import com.akmalmf.storyapp.domain.usecase.story.GetStoryWithMapUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 09/05/2023 19:42.
 * akmalmf007@gmail.com
 */
@HiltViewModel
class StoryMapViewModel @Inject constructor(
    private val storyMap: GetStoryWithMapUserCase
) : ViewModel() {
    private val _stories = MutableLiveData<Resource<StoriesResponse>>()
    val stories: LiveData<Resource<StoriesResponse>> get() = _stories


    fun getStoriesWithMap(){
        viewModelScope.launch {
            storyMap.invoke().collect {
                _stories.postValue(it)
            }
        }
    }
}