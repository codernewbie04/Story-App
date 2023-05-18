package com.akmalmf.storyapp.ui.main.story_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akmalmf.storyapp.databinding.LoadingStateStoriesBinding
import com.akmalmf.storyapp.domain.utils.toVisible

/**
 * Created by Akmal Muhamad Firdaus on 08/05/2023 22:21.
 * akmalmf007@gmail.com
 */
class LoadingAdapter(private val retry: () -> Unit): LoadStateAdapter<LoadingAdapter.LoadingViewHolder>(){
    override fun onBindViewHolder(
        holder: LoadingAdapter.LoadingViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingAdapter.LoadingViewHolder {
        val binding = LoadingStateStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingViewHolder(binding, retry)
    }

    inner class LoadingViewHolder(private val binding: LoadingStateStoriesBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonRetry.setOnClickListener { retry.invoke() }
        }
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error){
                binding.buttonRetry.toVisible()
            }
        }
    }
}