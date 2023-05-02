package com.akmalmf.storyapp.domain.usecase.story

import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.stories.StoriesResponse
import com.akmalmf.storyapp.domain.repository.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 11:59.
 * akmalmf007@gmail.com
 */
class GetStoryUseCase @Inject constructor(
    private val repository: StoryRepository
)  {
    operator fun invoke(): Flow<Resource<StoriesResponse>> {
        return flow {
            emit(Resource.loading())
            emit(repository.getStories())
        }.flowOn(Dispatchers.IO)
    }
}