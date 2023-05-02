package com.akmalmf.storyapp.ui.auth.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.akmalmf.storyapp.R
import com.akmalmf.storyapp.base.BaseFragment
import com.akmalmf.storyapp.data.abstraction.Status
import com.akmalmf.storyapp.databinding.FragmentRegisterBinding
import com.akmalmf.storyapp.domain.utils.getText
import com.akmalmf.storyapp.domain.utils.isValidEmail
import com.akmalmf.storyapp.domain.utils.toInvisible
import com.akmalmf.storyapp.domain.utils.toVisible
import com.akmalmf.storyapp.ui.components.PasswordEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val viewModel: RegisterViewModel by hiltNavGraphViewModels(R.id.auth_nav)
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        bi.buttonRegister.setOnClickListener {
            if (formValidation(bi.textInputName, bi.textInputEmail, bi.textInputPassword)) {
                viewModel.register(
                    bi.textInputName.getText(),
                    bi.textInputEmail.getText(),
                    bi.textInputPassword.getText()
                ).observe(requireActivity()) {
                    when (it.status) {
                        Status.LOADING -> {
                            bi.progressBar.toVisible()
                            bi.buttonRegister.toInvisible()
                        }

                        Status.SUCCESS -> {
                            if (it.data?.error == true) {
                                snackBarError(it.data.message)
                            } else {
                                snackBarSuccess("Berhasil mendaftar")
                                findNavController().navigate(
                                    RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(
                                        bi.textInputEmail.getText()
                                    )
                                )
                            }
                            bi.progressBar.toInvisible()
                            bi.buttonRegister.toVisible()
                        }

                        Status.ERROR -> {
                            it.data?.let { it1 -> snackBarError(it1.message) }
                            bi.progressBar.toInvisible()
                            bi.buttonRegister.toVisible()
                        }
                    }
                }
            }
        }
    }

    private fun formValidation(
        name: TextInputLayout,
        email: TextInputLayout,
        password: PasswordEditText
    ): Boolean {
        if (name.getText().isEmpty()) name.helperText = "Nama tidak boleh kosong!"
        else name.helperText = null

        if (email.getText().isEmpty()) email.helperText = "Masukan email dengan benar"
        else if (!isValidEmail(email.getText())) email.helperText = "Email tidak valid!"
        else email.helperText = null

        if (!password.isCorrectPassword() || !isValidEmail(email.getText()) || name.getText()
                .isEmpty()
        ) {
            return false
        }
        return true
    }

    override fun initObservable() {

    }


}