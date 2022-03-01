package com.tessalonika.onandcafe.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.base.BaseFragment
import com.tessalonika.onandcafe.base.enable
import com.tessalonika.onandcafe.base.showToast
import com.tessalonika.onandcafe.base.visible
import com.tessalonika.onandcafe.databinding.FragmentLoginBinding
import com.tessalonika.onandcafe.ui.ViewModelFactory

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory(requireActivity().application)
        val viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        binding.apply {
            viewModel.getIsLoading().observe(viewLifecycleOwner) {
                pbLogin.visible(it)
                btnLogin.enable(!it)
                btnLoginRegister.enable(!it)
            }
            viewModel.getOnError().observe(viewLifecycleOwner) {
                if(it.isNotEmpty()) showToast(requireContext(), it, Toast.LENGTH_SHORT)
            }
            viewModel.getIsSuccess().observe(viewLifecycleOwner) {
                if (it != null) {
                    if(it.isAdmin) {
                        Navigation
                            .findNavController(view)
                            .navigate(R.id.action_loginFragment_to_homeAdminActivity)
                    } else {
                        Navigation
                            .findNavController(view)
                            .navigate(R.id.action_loginFragment_to_homeActivity)
                    }
                }
            }

            btnLoginRegister.setOnClickListener {
                Navigation
                    .findNavController(it)
                    .navigate(R.id.action_loginFragment_to_registerFragment)
            }
            btnLogin.setOnClickListener {
                val username = tilUsername.editText?.text.toString()
                val password = tilPassword.editText?.text.toString()

                viewModel.authenticateUser(username, password)
            }
        }
    }

    override fun getViewBinding(): FragmentLoginBinding =
        FragmentLoginBinding.inflate(layoutInflater)
}