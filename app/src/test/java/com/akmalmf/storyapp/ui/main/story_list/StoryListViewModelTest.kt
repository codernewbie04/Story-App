package com.akmalmf.storyapp.ui.main.story_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback

import com.akmalmf.storyapp.MainDispatcherRule
import com.akmalmf.storyapp.data.repository.StoryPagingDataSource
import com.akmalmf.storyapp.domain.model.stories.StoryResponse

import com.akmalmf.storyapp.domain.repository.SharePrefRepository
import com.akmalmf.storyapp.domain.repository.StoryRepository
import com.akmalmf.storyapp.fakerepo.FakeStoryRepository
import com.akmalmf.storyapp.getOrAwaitValue

import com.akmalmf.storyapp.mock
import com.akmalmf.storyapp.ui.main.story_list.adapter.StoriesAdapter
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.lenient

import org.mockito.Mockito.reset


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryListViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()
    private val storyRepository = mock<StoryRepository>()
    private val sharePrefRepository = mock<SharePrefRepository>()


    @Before
    fun initTest() {
        reset(sharePrefRepository)
    }

    @Test
    fun `test stories - Success`() = runTest(UnconfinedTestDispatcher()) {
        val fakeRepo = FakeStoryRepository().getStories()

        val data: PagingData<StoryResponse> = DifferStory.snapshot(fakeRepo.data?.listStory ?: emptyList())
        val expectedStories = MutableLiveData<PagingData<StoryResponse>>()
        expectedStories.value = data
        Mockito.`when`(storyRepository.getStories()).thenReturn(expectedStories)

        val viewModel = StoryListViewModel(storyRepository, sharePrefRepository)
        val actualStories: PagingData<StoryResponse> = viewModel.stories.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = pagingDataUpdate,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStories)
        assertNotNull(actualStories)
        assertEquals(fakeRepo.data!!.listStory.size, differ.snapshot().size)
        Assert.assertEquals(fakeRepo.data!!.listStory, differ.snapshot())
        assertEquals(fakeRepo.data!!.listStory[0], differ.snapshot()[0])
    }

    @Test
    fun `test stories - No Data`() = runTest(UnconfinedTestDispatcher()) {
        // Create empty mock PagingData
        val fakeRepo = FakeStoryRepository().emptyResponse()

        val data: PagingData<StoryResponse> = DifferStory.snapshot(fakeRepo.data?.listStory ?: emptyList())
        val expectedStories = MutableLiveData<PagingData<StoryResponse>>()
        expectedStories.value = data
        Mockito.`when`(storyRepository.getStories()).thenReturn(expectedStories)


        val viewModel = StoryListViewModel(storyRepository, sharePrefRepository)

        val actualStories: PagingData<StoryResponse> = viewModel.stories.getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = pagingDataUpdate,
            workerDispatcher = Dispatchers.Main,
        )

        differ.submitData(actualStories)
        Assert.assertEquals(0, differ.snapshot().size)
    }

    private val pagingDataUpdate = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }


}

class DifferStory : PagingSource<Int, LiveData<List<StoryResponse>>>() {
    companion object {
        fun snapshot(items: List<StoryResponse>): PagingData<StoryResponse> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoryResponse>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<StoryResponse>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

