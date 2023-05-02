package com.akmalmf.storyapp.ui.main.story_list

import android.animation.Animator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.akmalmf.storyapp.R
import com.akmalmf.storyapp.base.BaseFragmentWithObserve
import com.akmalmf.storyapp.data.abstraction.Status
import com.akmalmf.storyapp.databinding.FragmentStoryListBinding
import com.akmalmf.storyapp.domain.utils.toGone
import com.akmalmf.storyapp.domain.utils.toVisible
import com.akmalmf.storyapp.ui.main.story_list.adapter.StoriesAdapter


class StoryListFragment : BaseFragmentWithObserve<FragmentStoryListBinding>() {
    private val viewModel: StoryListViewModel by hiltNavGraphViewModels(R.id.story_nav)

    private val adapter by lazy { StoriesAdapter() }

    private var isFabOpen = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStoryListBinding
        get() = FragmentStoryListBinding::inflate

    override fun initView() {
        bi.rvStories.setHasFixedSize(true)
        bi.rvStories.adapter = adapter
        adapter.onItemClick = {
            findNavController().navigate(StoryListFragmentDirections.actionStoryListFragmentToStoryDetailFragment(it.id))
        }

        bi.fab.setOnClickListener {
            if(isFabOpen){
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

        bi.fabAddStory.setOnClickListener {
            findNavController().navigate(StoryListFragmentDirections.actionStoryListFragmentToStoryCreateFragment())
        }

        bi.fabLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.action_storyListFragment_to_authActivity)
            requireActivity().finishAffinity()
        }
        val refresh: Boolean = arguments?.getBoolean("need_refresh", false) == true
        if (refresh) {
            viewModel.getStories()
        }
    }

    override fun onResume() {
        super.onResume()
        if(isFabOpen){
            openFabMenu()
        } else {
            closeFABMenu()
        }
    }

    private fun openFabMenu() {
        isFabOpen = true
        bi.fabLayoutAddStory.toVisible()
        bi.fabLayoutLogout.toVisible()
        bi.fabBGLayout.toVisible()
        bi.fab.animate().rotationBy(180F)
        bi.fabLayoutAddStory.animate().translationY(-200F)
        bi.fabLayoutLogout.animate().translationY(-100F)
    }

    private fun closeFABMenu() {
        isFabOpen = false
        bi.fabBGLayout.toGone()
        bi.fab.animate().rotation(0F)
        bi.fabLayoutAddStory.animate().translationY(0F)
        bi.fabLayoutLogout.animate().translationY(0F)
        bi.fabLayoutLogout.animate().setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                if (!isFabOpen) {
                    bi.fabLayoutAddStory.toGone()
                    bi.fabLayoutLogout.toGone()
                }
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }

    override fun initObservable() {
        viewModel.stories.observe(this){
            when(it.status){
                Status.LOADING -> {
                    bi.apply {
                        shimmerStories.toVisible()
                        shimmerStories.startShimmer()
                    }

                }
                Status.SUCCESS -> {
                    if (it.data?.error == true){
                        snackBarError(it.data.message)
                    } else {
                        it.data?.listStory.let { it1 ->
                            it1?.let { it2 -> adapter.setItem(it2.toMutableList()) }
                        }
                    }

                    bi.apply {
                        shimmerStories.toGone()
                        shimmerStories.stopShimmer()
                        rvStories.toVisible()
                    }
                }
                Status.ERROR -> {
                    bi.apply {
                        shimmerStories.toGone()
                        shimmerStories.stopShimmer()
                    }
                    (it.data?.message ?: it.message)?.let { it1 -> snackBarError(it1) }
                    if(it.code == 401){
                        viewModel.logout()
                    }
                }
            }
        }
    }
}