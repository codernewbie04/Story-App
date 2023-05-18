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
 * Created by Akmal Muhamad Firdaus on 09/05/2023 19:39.
 * akmalmf007@gmail.com
 */
class GetStoryWithMapUserCase @Inject constructor(
    private val repository: StoryRepository
) {
    operator fun invoke(): Flow<Resource<StoriesResponse>> {
        return flow {
            emit(Resource.loading())
            emit(repository.getStoriesMap())
        }.flowOn(Dispatchers.IO)
    }
}