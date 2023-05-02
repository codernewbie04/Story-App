package com.akmalmf.storyapp.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        val helperTextColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red_400)) // Replace `your_color` with the desired color value
        setHelperTextColor(helperTextColor)
        val passwordToggleDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.password_selector)
        isPasswordVisibilityToggleEnabled = true
        passwordVisibilityToggleDrawable = passwordToggleDrawable
    }

    private var editText: TextInputEditText? = null
    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        child.let {
            super.addView(it, index, params)
        }
        if (child is TextInputEditText) {
            editText = child
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    onTextChanged(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }

    fun isCorrectPassword():Boolean = editText.toString().length > 8

    private fun onTextChanged(s: String) {
        helperText = s.takeIf { it.length < 8 }?.let { "Password minimal 8 character!" }
    }
}