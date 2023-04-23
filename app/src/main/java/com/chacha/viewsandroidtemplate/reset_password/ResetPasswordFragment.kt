package com.chacha.viewsandroidtemplate.reset_password

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chacha.viewsandroidtemplate.R
import com.chacha.viewsandroidtemplate.databinding.ResetPasswordFragmentBinding

class ResetPasswordFragment : Fragment() {
    private var _binding: ResetPasswordFragmentBinding? = null
    private val binding get()= _binding!!

    private lateinit var viewModel: ResetPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ResetPasswordFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}