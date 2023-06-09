@file:Suppress("BooleanMethodIsAlwaysInverted")

package com.akmalmf.storyapp.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.akmalmf.storyapp.R
import com.akmalmf.storyapp.domain.utils.isValidEmail
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Akmal Muhamad Firdaus on 02/05/2023 21:58.
 * akmalmf007@gmail.com
 */
@Suppress("BooleanMethodIsAlwaysInverted")
class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {
    init {
        isHelperTextEnabled = true
        val helperTextColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_400))
        setHelperTextColor(helperTextColor)
    }
    var currentString = ""

    private var editText: TextInputEditText? = null
    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        child.let {
            super.addView(it, index, params)
        }
        if (child is TextInputEditText) {
            editText = child
            editText?.addTextChangedListener(onTextChanged = {s, _, _, _ ->
                onTextChanged(s.toString())
                currentString = s.toString()
            })
        }
    }

    fun isEmailCorrect():Boolean = isValidEmail(currentString) && currentString.isNotEmpty()

    private fun onTextChanged(s: String) {
        helperText = s.takeIf { !isValidEmail(it) }?.let { "Email tidak valid!" }
    }
}