package com.tessalonika.onandcafe.ui.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.tessalonika.onandcafe.base.*
import com.tessalonika.onandcafe.databinding.FragmentRegisterBinding
import com.tessalonika.onandcafe.model.User
import com.tessalonika.onandcafe.ui.ViewModelFactory

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory(requireActivity().application)
        val viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        binding.apply {
            viewModel.getIsLoading().observe(viewLifecycleOwner) {
                pbRegister.visible(it)
                btnRegister.enable(!it)
                btnRegisterLogin.enable(!it)
            }
            viewModel.getOnError().observe(viewLifecycleOwner) {
                if(it.isNotEmpty()) showToast(requireContext(), it, Toast.LENGTH_SHORT)
            }
            viewModel.getIsSuccess().observe(viewLifecycleOwner) {
                if (it > 0) Navigation.findNavController(view).popBackStack()
            }

            btnRegisterLogin.setOnClickListener {
                Navigation.findNavController(it).popBackStack()
            }

            btnRegister.setOnClickListener {
                val username = tilRegisterUsername.editText?.text.toString()
                val email = tilRegisterEmail.editText?.text.toString()
                val password = tilRegisterPassword.editText?.text.toString()

                if(invalidateAuthForm(requireContext(), username, email, password)) {
                    viewModel.registerUser(User(0, username, email, password))
                }
            }
        }
    }

    override fun getViewBinding(): FragmentRegisterBinding =
        FragmentRegisterBinding.inflate(layoutInflater)
}