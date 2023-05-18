package com.akmalmf.storyapp.ui.main.story_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.akmalmf.storyapp.domain.model.stories.StoryResponse
import com.akmalmf.storyapp.domain.repository.SharePrefRepository
import com.akmalmf.storyapp.domain.repository.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 12:05.
 * akmalmf007@gmail.com
 */
@HiltViewModel
class StoryListViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val sharPref: SharePrefRepository
) : ViewModel() {
    val stories: LiveData<PagingData<StoryResponse>> = storyRepository.getStories().cachedIn(viewModelScope)
    fun logout() {
        sharPref.setAccessToken("")
    }
}