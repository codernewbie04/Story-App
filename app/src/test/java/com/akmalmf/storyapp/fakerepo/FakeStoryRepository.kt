package com.akmalmf.storyapp.fakerepo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.akmalmf.storyapp.data.abstraction.Resource
import com.akmalmf.storyapp.data.abstraction.Status
import com.akmalmf.storyapp.domain.model.stories.AddStoryResponse
import com.akmalmf.storyapp.domain.model.stories.DetailStoryResponse
import com.akmalmf.storyapp.domain.model.stories.StoriesResponse
import com.akmalmf.storyapp.domain.model.stories.StoryResponse
import com.akmalmf.storyapp.domain.repository.StoryRepository
import okhttp3.MultipartBody

/**
 * Created by Akmal Muhamad Firdaus on 10/05/2023 18:45.
 * akmalmf007@gmail.com
 */
class FakeStoryRepository {
    fun getStories(): Resource<StoriesResponse> {
        val story1 = StoryResponse("story-FvU4u0Vp2S3PMsFg","Akmal", "Lorem Ipsum", "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png", "2023-05-10T06:34:18.598Z", -10.212, -16.002)
        val story2 = StoryResponse("story-FvU5uSVp2S3PMsFg","Muhamad", "Lorem Ipsum", "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png", "2023-05-10T06:34:18.598Z", -10.212, -16.002)
        val story3 = StoryResponse("story-FvU4u0Po&sH4MsFg","Firdaus", "Lorem Ipsum", "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png", "2023-05-10T06:34:18.598Z", -10.212, -16.002)
        val story4 = StoryResponse("story-7DuS60Vp2S3PMsFg","Lorem", "Lorem Ipsum", "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png", "2023-05-10T06:34:18.598Z", -10.212, -16.002)

        val storyList: List<StoryResponse> = listOf(story1, story2, story3,story4)
        val storiesResponse = StoriesResponse(false, storyList, "OK!")
        return Resource<StoriesResponse>(status= Status.SUCCESS, data = storiesResponse, code = 200, message = "ok")
    }

    fun emptyResponse():Resource<StoriesResponse>{
        return Resource<StoriesResponse>(status= Status.SUCCESS, data = null, code = 200, message = "ok")
    }
}