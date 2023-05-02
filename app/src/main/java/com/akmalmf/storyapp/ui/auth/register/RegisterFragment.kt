package com.akmalmf.storyapp.ui.auth.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.akmalmf.storyapp.R
import com.akmalmf.storyapp.base.BaseFragment
import com.akmalmf.storyapp.data.abstraction.Status
import com.akmalmf.storyapp.databinding.FragmentRegisterBinding
import com.akmalmf.storyapp.domain.utils.getText
import com.akmalmf.storyapp.domain.utils.toInvisible
import com.akmalmf.storyapp.domain.utils.toVisible
import com.akmalmf.storyapp.ui.components.EmailEditText
import com.akmalmf.storyapp.ui.components.PasswordEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val viewModel: RegisterViewModel by hiltNavGraphViewModels(R.id.auth_nav)
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate

    override fun initView() {
        setupViews()
    }

    private fun setupViews() {
        bi.appBar.textToolbar.text = "Daftar"
        bi.buttonRegister.setOnClickListener {
            if (formValidation(bi.textInputName, bi.textInputEmail, bi.textInputPassword)) {
                viewModel.register(
                    bi.textInputName.getText(),
                    bi.textInputEmail.getText(),
                    bi.textInputPassword.getText()
                ).observe(requireActivity()) {
                    when (it.status) {
                        Status.LOADING -> {
                            bi.apply {
                                progressBar.toVisible()
                                buttonRegister.toInvisible()
                            }
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
                            bi.apply {
                                progressBar.toInvisible()
                                buttonRegister.toVisible()
                            }
                        }

                        Status.ERROR -> {
                            bi.apply {
                                progressBar.toInvisible()
                                buttonRegister.toVisible()
                            }
                            (it.data?.message ?: it.message)?.let { it1 -> snackBarError(it1) }
                        }
                    }
                }
            }
        }
        bi.txtLogin.setOnClickListener{
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(""))
        }
    }

    private fun formValidation(
        name: TextInputLayout,
        email: EmailEditText,
        password: PasswordEditText
    ): Boolean {
        if (name.getText().isEmpty()) name.helperText = "Nama tidak boleh kosong!"
        else name.helperText = null

        if(email.getText().isEmpty()) email.helperText = "Email tidak boleh kosong!"

        if(password.getText().isEmpty()) password.helperText = "Password tidak boleh kosong!"

        return password.isCorrectPassword() && email.isEmailCorrect() && name.getText().isNotEmpty()
    }
}