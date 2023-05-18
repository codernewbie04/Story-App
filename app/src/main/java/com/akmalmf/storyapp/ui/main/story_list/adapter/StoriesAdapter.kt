package com.akmalmf.storyapp.ui.main.story_list.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.akmalmf.storyapp.R
import com.akmalmf.storyapp.databinding.ItemStoriesBinding
import com.akmalmf.storyapp.domain.model.stories.StoryResponse

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 12:20.
 * akmalmf007@gmail.com
 */
class StoriesAdapter : PagingDataAdapter<StoryResponse, StoriesAdapter.VHolder>(DIFF_CALLBACK) {
    var onItemClick: ((StoryResponse) -> Unit)? = null
    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemStoriesBinding.bind(itemView)
        fun onBind(data: StoryResponse) {
            binding.apply {
                imageStory.load(data.photoUrl) {
                    placeholder(R.drawable.ic_image_placeholder)
                    error(R.drawable.ic_image_error)
                }
                textName.text = data.name
                textDescription.text = data.description
                root.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_stories, parent, false)
        )
    }
    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.onBind(item)
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryResponse>() {
            override fun areItemsTheSame(oldItem: StoryResponse, newItem: StoryResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryResponse, newItem: StoryResponse): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}