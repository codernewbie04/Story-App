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
import com.akmalmf.storyapp.ui.components.PasswordEditText
import com.google.android.material.textfield.TextInputLayout
class LoginFragment() : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by hiltNavGraphViewModels(R.id.auth_nav)
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun initObservable() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val email: String? = arguments?.getString("email")
        if (email != null){
            bi.textInputEmail.editText?.setText(email)
        }
        setupViews()
    }

    private fun setupViews() {
        bi.textRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        bi.buttonLogin.setOnClickListener {
            if(formValidation(bi.textInputEmail, bi.textInputPassword)){
                viewModel.login(bi.textInputEmail.getText(), bi.textInputPassword.getText()).observe(requireActivity()){
                    when(it.status){
                        Status.LOADING->{
                            bi.progressBar.toVisible()
                            bi.buttonLogin.toInvisible()
                        }
                        Status.SUCCESS->{
                            if(it.data?.error == true){
                                snackBarError(it.data.message)
                            } else {
                                val resData = it.data?.loginResult
                                resData?.let { loginRes -> viewModel.setAccessToken(loginRes.token) }
                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
                                requireActivity().finish()
                            }
                            bi.progressBar.toInvisible()
                            bi.buttonLogin.toVisible()
                        }
                        Status.ERROR ->{
                            it.data?.let { it1 -> snackBarError(it1.message) }
                            bi.progressBar.toInvisible()
                            bi.buttonLogin.toVisible()
                        }
                    }
                }
            }
        }
    }

    private fun formValidation(email: TextInputLayout, password: PasswordEditText):Boolean{
        if (email.getText().isEmpty()) email.helperText = "Masukan email dengan benar"
        else if (!isValidEmail(email.getText())) email.helperText = "Email tidak valid!"
        else email.helperText = null

        if (!password.isCorrectPassword() || !isValidEmail(email.getText())) {
            return false
        }

        return true
    }
}