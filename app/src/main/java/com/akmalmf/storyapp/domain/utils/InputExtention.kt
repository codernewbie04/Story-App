package com.akmalmf.storyapp.domain.utils

import com.akmalmf.storyapp.ui.components.PasswordEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Created by Akmal Muhamad Firdaus on 01/05/2023 12:27.
 * akmalmf007@gmail.com
 */
fun TextInputLayout.getText(): String {
    return editText?.text.toString().trim()
}
fun PasswordEditText.getText(): String {
    return editText?.text.toString().trim()
}