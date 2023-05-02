package com.akmalmf.storyapp.ui.main.story_detail


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.akmalmf.storyapp.R
import com.akmalmf.storyapp.base.BaseFragment
import com.akmalmf.storyapp.data.abstraction.Status
import com.akmalmf.storyapp.databinding.FragmentStoryDetailBinding
import com.akmalmf.storyapp.domain.utils.toInvisible
import com.akmalmf.storyapp.domain.utils.toVisible
import dagger.hilt.EntryPoint


class StoryDetailFragment : BaseFragment<FragmentStoryDetailBinding>() {
    private val viewModel: StoryDetailViewModel by hiltNavGraphViewModels(R.id.story_nav)

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStoryDetailBinding
        get() = FragmentStoryDetailBinding::inflate

    override fun initView() {
        val id: String? = arguments?.getString("id")
        if (id == null) {
            findNavController().navigate(
                StoryDetailFragmentDirections.actionStoryDetailFragmentToStoryListFragment(
                    false
                )
            )
        } else {
            viewModel.detailStory(id).observe(this) {
                when (it.status) {
                    Status.LOADING -> {
                        bi.apply {
                            imageStory.toInvisible()
                            textDescription.toInvisible()
                            shimmerStory.toVisible()
                            shimmerStory.startShimmer()
                        }

                    }

                    Status.SUCCESS -> {
                        bi.apply {
                            val story = it.data?.story
                            if (story != null) {
                                imageStory.load(story.photoUrl) {
                                    placeholder(R.drawable.ic_image_placeholder)
                                    error(R.drawable.ic_image_error)
                                }
                                appBar.textToolbar.text = story.name
                                textDescription.text = story.description
                                textCreatedAt.text = story.createdAt
                            }
                            imageStory.toVisible()
                            textDescription.toVisible()
                            shimmerStory.toInvisible()
                            shimmerStory.stopShimmer()
                        }
                    }

                    Status.ERROR -> {
                        (it.data?.message ?: it.message)?.let { it1 -> snackBarError(it1) }
                        findNavController().navigate(
                            StoryDetailFragmentDirections.actionStoryDetailFragmentToStoryListFragment(
                                false
                            )
                        )
                    }
                }
            }
        }
    }

}