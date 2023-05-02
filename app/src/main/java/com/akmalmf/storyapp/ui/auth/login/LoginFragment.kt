package com.akmalmf.storyapp.ui.auth.login


import android.os.Bundle
import androidx.navigation.fragment.findNavController
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.akmalmf.storyapp.R
import com.akmalmf.storyapp.base.BaseFragment
import com.akmalmf.storyapp.data.abstraction.Status
import com.akmalmf.storyapp.databinding.FragmentLoginBinding
import com.akmalmf.storyapp.domain.utils.getText
import com.akmalmf.storyapp.domain.utils.isValidEmail
import com.akmalmf.storyapp.domain.utils.toInvisible
import com.akmalmf.storyapp.domain.utils.toVisible
import com.akmalmf.storyapp.ui.components.EmailEditText
import com.akmalmf.storyapp.ui.components.PasswordEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment() : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by hiltNavGraphViewModels(R.id.auth_nav)
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun initView() {
        bi.appBar.textToolbar.text = "Masuk"
        val email: String? = arguments?.getString("email")
        if (email != null) {
            bi.textInputEmail.editText?.setText(email)
        }

        bi.textRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        bi.buttonLogin.setOnClickListener {
            if (formValidation(bi.textInputEmail, bi.textInputPassword)) {
                viewModel.login(bi.textInputEmail.getText(), bi.textInputPassword.getText())
                    .observe(requireActivity()) {
                        when (it.status) {
                            Status.LOADING -> {
                                bi.apply {
                                    progressBar.toVisible()
                                    buttonLogin.toInvisible()
                                }
                            }

                            Status.SUCCESS -> {
                                if (it.data?.error == true) {
                                    snackBarError(it.data.message)
                                } else {
                                    val resData = it.data?.loginResult
                                    resData?.let { loginRes -> viewModel.setAccessToken(loginRes.token) }
                                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
                                    requireActivity().finish()
                                }
                                bi.apply {
                                    progressBar.toInvisible()
                                    buttonLogin.toVisible()
                                }
                            }

                            Status.ERROR -> {
                                it.data?.let { it1 -> snackBarError(it1.message) }
                                bi.apply {
                                    progressBar.toInvisible()
                                    buttonLogin.toVisible()
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun formValidation(email: EmailEditText, password: PasswordEditText): Boolean {
        if(email.getText().isEmpty()) email.helperText = "Email tidak boleh kosong!"
        if(password.getText().isEmpty()) password.helperText = "Password tidak boleh kosong!"

        return password.isCorrectPassword() && email.isEmailCorrect()
    }
}