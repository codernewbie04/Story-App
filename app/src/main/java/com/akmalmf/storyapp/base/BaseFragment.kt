package com.akmalmf.storyapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.akmalmf.storyapp.R
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 11:10.
 * akmalmf007@gmail.com
 */

abstract class BaseFragment<VB: ViewBinding> : Fragment() {

    private var _bi: VB? = null
    protected val bi: VB get() = _bi!!

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    abstract fun initView()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _bi = bindingInflater(inflater, container, false)
        return _bi!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bi = null
    }

    fun snackBarSuccess(message: String) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.green_300))
            .show()
    }

    fun snackBarError(message: String) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red_400))
            .show()
    }
}