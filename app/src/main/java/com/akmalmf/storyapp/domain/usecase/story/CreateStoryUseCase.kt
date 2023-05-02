package com.akmalmf.storyapp.domain.usecase.story

import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.domain.model.stories.AddStoryResponse
import com.akmalmf.storyapp.domain.repository.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 11:59.
 * akmalmf007@gmail.com
 */
class CreateStoryUseCase @Inject constructor(
    private val repository: StoryRepository
) {
    operator fun invoke(description: String, photo: MultipartBody.Part): Flow<Resource<AddStoryResponse>> {
        return flow {
            emit(Resource.loading())
            emit(repository.createStory(description, photo))
        }.flowOn(Dispatchers.IO)
    }
}