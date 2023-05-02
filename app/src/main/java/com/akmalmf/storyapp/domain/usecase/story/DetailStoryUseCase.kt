package com.akmalmf.storyapp.domain.usecase.story

import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.stories.DetailStoryResponse
import com.akmalmf.storyapp.domain.model.stories.StoriesResponse
import com.akmalmf.storyapp.domain.model.stories.StoryResponse
import com.akmalmf.storyapp.domain.repository.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 21:02.
 * akmalmf007@gmail.com
 */
class DetailStoryUseCase @Inject constructor(
    private val repository: StoryRepository
)  {
    operator fun invoke(id:String): Flow<Resource<DetailStoryResponse>> {
        return flow {
            emit(Resource.loading())
            emit(repository.detailStory(id))
        }.flowOn(Dispatchers.IO)
    }
}