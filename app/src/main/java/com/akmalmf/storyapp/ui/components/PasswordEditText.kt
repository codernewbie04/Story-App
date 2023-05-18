package com.akmalmf.storyapp.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.akmalmf.storyapp.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 10:16.
 * akmalmf007@gmail.com
 */
class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {
    init {
        isHelperTextEnabled = true
        val helperTextColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_400))
        setHelperTextColor(helperTextColor)
        val passwordToggleDrawable: Drawable? =
            ContextCompat.getDrawable(context, R.drawable.password_selector)
        isPasswordVisibilityToggleEnabled = true
        passwordVisibilityToggleDrawable = passwordToggleDrawable
    }

    private var editText: TextInputEditText? = null
    var length = 0
    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        child.let {
            super.addView(it, index, params)
        }
        if (child is TextInputEditText) {
            editText = child
            editText?.addTextChangedListener(onTextChanged = { s, _, _, _ ->
                onTextChanged(s.toString())
                length = s.toString().length
            })
        }
    }

    fun isCorrectPassword(): Boolean = length >= 8

    private fun onTextChanged(s: String) {
        helperText = s.takeIf { it.length < 8 }?.let { "Password minimal 8 character!" }
    }
}