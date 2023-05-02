package com.akmalmf.storyapp.ui.main.story_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.akmalmf.storyapp.R
import com.akmalmf.storyapp.base.BaseFragment
import com.akmalmf.storyapp.data.abstraction.Status
import com.akmalmf.storyapp.databinding.FragmentStoryListBinding
import com.akmalmf.storyapp.ui.main.story_list.adapter.StoriesAdapter


class StoryListFragment : BaseFragment<FragmentStoryListBinding>() {
    private val viewModel: StoryListViewModel by hiltNavGraphViewModels(R.id.story_nav)

    private val adapter by lazy { StoriesAdapter() }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStoryListBinding
        get() = FragmentStoryListBinding::inflate

    override fun initView() {
        bi.rvStories.adapter = adapter
        bi.rvStories.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }

    override fun initObservable() {
        viewModel.stories.observe(this){
            when(it.status){
                Status.LOADING -> {
                    bi.shimmerStories.visibility = View.VISIBLE
                    bi.shimmerStories.startShimmer()
                }
                Status.SUCCESS -> {
                    it.data?.listStory.let { it1 ->
                        it1?.let { it2 -> adapter.setItem(it2.toMutableList()) }
                    }
                    bi.shimmerStories.visibility = View.VISIBLE
                    bi.shimmerStories.startShimmer()
                }
                Status.ERROR -> {
                    bi.shimmerStories.visibility = View.VISIBLE
                    bi.shimmerStories.startShimmer()

                }
            }
        }
    }
}