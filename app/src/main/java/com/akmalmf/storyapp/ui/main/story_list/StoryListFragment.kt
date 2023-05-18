package com.akmalmf.storyapp.ui.main.story_list

import android.animation.Animator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.akmalmf.storyapp.R
import com.akmalmf.storyapp.base.BaseFragmentWithObserve
import com.akmalmf.storyapp.data.abstraction.Status
import com.akmalmf.storyapp.databinding.FragmentStoryListBinding
import com.akmalmf.storyapp.domain.utils.toGone
import com.akmalmf.storyapp.domain.utils.toVisible
import com.akmalmf.storyapp.ui.main.story_list.adapter.LoadingAdapter
import com.akmalmf.storyapp.ui.main.story_list.adapter.StoriesAdapter


class StoryListFragment : BaseFragmentWithObserve<FragmentStoryListBinding>() {
    private val viewModel: StoryListViewModel by hiltNavGraphViewModels(R.id.story_nav)

    private val adapter by lazy { StoriesAdapter() }

    private var isFabOpen = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStoryListBinding
        get() = FragmentStoryListBinding::inflate

    override fun initView() {
        bi.apply {
            rvStories.setHasFixedSize(true)
            rvStories.adapter = adapter
            rvStories.adapter = adapter.withLoadStateHeaderAndFooter(
                header = LoadingAdapter {
                    adapter.retry()
                },
                footer = LoadingAdapter {
                    adapter.retry()
                }
            )
        }
        adapter.onItemClick = {
            findNavController().navigate(
                StoryListFragmentDirections.actionStoryListFragmentToStoryDetailFragment(
                    it.id
                )
            )
        }

        bi.fab.setOnClickListener {
            if (isFabOpen) {
                closeFABMenu()
            } else {
                openFabMenu()
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isFabOpen) {
                    closeFABMenu()
                } else {
                    requireActivity().finish()
                }
            }
        })

        bi.fabMap.setOnClickListener {
            findNavController().navigate(StoryListFragmentDirections.actionStoryListFragmentToStoryMapFragment())
        }

        bi.fabAddStory.setOnClickListener {
            findNavController().navigate(StoryListFragmentDirections.actionStoryListFragmentToStoryCreateFragment())
        }

        bi.fabLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_storyListFragment_to_authActivity)
            requireActivity().finishAffinity()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isFabOpen) {
            openFabMenu()
        } else {
            closeFABMenu()
        }
        adapter.refresh()
    }

    private fun openFabMenu() {
        isFabOpen = true
        bi.apply {
            fabLayoutAddStory.toVisible()
            fabLayoutLogout.toVisible()
            fabLayoutMap.toVisible()
            fabBGLayout.toVisible()
            fab.animate().rotationBy(180F)
            fabLayoutMap.animate().translationY(-300F)
            fabLayoutAddStory.animate().translationY(-200F)
            fabLayoutLogout.animate().translationY(-100F)
        }
    }

    private fun closeFABMenu() {
        isFabOpen = false
        bi.apply {
            fabBGLayout.toGone()
            fab.animate().rotation(0F)
            fabLayoutMap.animate().translationY(0F)
            fabLayoutAddStory.animate().translationY(0F)
            fabLayoutLogout.animate().translationY(0F)
            fabLayoutLogout.animate().setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    if (!isFabOpen) {
                        bi.apply {
                            fabLayoutAddStory.toGone()
                            fabLayoutLogout.toGone()
                            fabLayoutMap.toGone()
                        }
                    }
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })
        }
    }


    override fun initObservable() {
        viewModel.stories.observe(viewLifecycleOwner) { data ->
            run {
                if (data != null) {
                    adapter.submitData(viewLifecycleOwner.lifecycle, data)
                }
            }

        }
    }
}