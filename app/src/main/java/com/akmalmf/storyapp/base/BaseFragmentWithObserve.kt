package com.akmalmf.storyapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 19:37.
 * akmalmf007@gmail.com
 */
abstract class BaseFragmentWithObserve<VB: ViewBinding>() :
    BaseFragment<VB>() {
    abstract override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    abstract override fun initView()
    abstract fun initObservable()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservable()
    }

}