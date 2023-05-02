package com.akmalmf.storyapp.domain.utils

import android.view.View

/**
 * Created by Akmal Muhamad Firdaus on 01/05/2023 12:17.
 * akmalmf007@gmail.com
 */
fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toInvisible() {
    visibility = View.INVISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

