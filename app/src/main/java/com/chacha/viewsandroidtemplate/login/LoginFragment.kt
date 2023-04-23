package com.chacha.viewsandroidtemplate.login

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.chacha.viewsandroidtemplate.R
import com.chacha.viewsandroidtemplate.databinding.LoginFragmentBinding
import com.chacha.viewsandroidtemplate.register.RegisterActions
import com.chacha.viewsandroidtemplate.register.RegisterUIState
import com.chacha.viewsandroidtemplate.util.observeEvent
import com.chacha.viewsandroidtemplate.util.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LoginFragment : Fragment(R.layout.login_fragment) {
    private val binding by viewBinding(LoginFragmentBinding::bind)
    private lateinit var viewModel: LoginViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setOnClickListener()

    }

    private fun setUpObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                LoginUiState.Loading ->{
                    //renderLoading(loading = true)
                }
                is LoginUiState.Error -> {
                    //renderError(errorTitle = it.title, errorMessage = it.message)
                }
                is LoginUiState.Message ->{
                    //renderSuccess(it.message)
                }
                else -> {}
            }
        }

        viewModel.interactions.observeEvent(viewLifecycleOwner) {
            when (it) {
                is LoginActions.Navigate -> findNavController().navigate(it.destination)
                is LoginActions.BottomNavigate -> showDialog(it.bottomSheetDialogFragment)
            }
        }
    }

    private fun showDialog(bottomSheetDialogFragment: BottomSheetDialogFragment) {
        bottomSheetDialogFragment.show(parentFragmentManager,bottomSheetDialogFragment.tag)
    }

    private fun setOnClickListener(){
        binding.apply {
            loginButton.setOnClickListener {
                validateUserFields()
            }
        }
    }

    private fun validateUserFields() {
        binding.apply {
            when {
                inputEmailPhoneNumber.text.toString().isEmpty() -> {
                    Toast.makeText(
                        requireContext(), "Enter Your Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                inputLoginPassword.text.toString().isEmpty() -> {
                    Toast.makeText(
                        requireContext(), "Enter Your Password",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                else -> {
                    viewModel.firstTimeLogin(
                        email = binding.inputEmailPhoneNumber.text.toString(),
                        password = binding.inputLoginPassword.text.toString()


                    )
                    loading.isVisible = true
                    onLoginFinished()
                }

            }
        }
    }

    private fun onLoginFinished() {
        val sharedPref = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}