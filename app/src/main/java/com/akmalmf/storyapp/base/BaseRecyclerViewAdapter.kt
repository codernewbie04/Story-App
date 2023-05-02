package com.akmalmf.storyapp.base

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 12:18.
 * akmalmf007@gmail.com
 */
abstract class BaseRecyclerViewAdapter<VH : RecyclerView.ViewHolder, T> :
    RecyclerView.Adapter<VH>() {

    protected var items: MutableList<T> = mutableListOf()

    var onItemClick: ((T) -> Unit)? = null

    abstract fun onBindViewHolder(holder: VH, item: T, position: Int)


    override fun getItemCount(): Int {
        return this.items.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, this.items[position], position)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    fun getItem(position: Int): T? = items[position]

    private fun addItem(item: MutableList<T>) {
        clear()
        this.items.addAll(item)
        this.notifyDataSetChanged()
    }

    private fun clear() {
        this.items.clear()
    }

    fun setItem(item: MutableList<T>) {
        clear()
        addItem(item)
    }

    fun getItem(): MutableList<T> {
        return items
    }
}