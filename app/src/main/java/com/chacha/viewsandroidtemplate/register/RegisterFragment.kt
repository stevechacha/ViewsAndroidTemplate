package com.chacha.viewsandroidtemplate.register

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
import com.chacha.viewsandroidtemplate.databinding.RegisterFragmentBinding
import com.chacha.viewsandroidtemplate.util.observeEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RegisterFragment : Fragment() {
    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RegisterFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setOnclickListener()
    }

    private fun setOnclickListener() {
        binding.apply {
            registerButton.setOnClickListener {
                userValidation()
            }
        }
    }

    private fun setUpObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                 RegisterUIState.Loading ->{
                     renderLoading(loading = true)
                 }
                is RegisterUIState.Error -> {
                    //renderError(errorTitle = it.title, errorMessage = it.message)
                }
                is RegisterUIState.Message ->{
                    renderSuccess(it.message)
                }
                else -> {}
            }
        }

        viewModel.interactions.observeEvent(viewLifecycleOwner) {
            when (it) {
                is RegisterActions.Navigate -> findNavController().navigate(it.destination)
                is RegisterActions.BottomNavigate -> showDialog(it.bottomSheetDialogFragment)
            }
        }
    }

    private fun renderLoading(loading: Boolean) {
        binding.loading.isVisible = binding.textView2.isVisible


    }

    private fun renderSuccess(message: String?) {
        binding.apply {
            loading.isVisible = false
            registerButton.isEnabled = true
            Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialog(bottomSheetDialogFragment: BottomSheetDialogFragment) {
        bottomSheetDialogFragment.show(parentFragmentManager,bottomSheetDialogFragment.tag)
    }

    private fun userValidation(){

    }

}