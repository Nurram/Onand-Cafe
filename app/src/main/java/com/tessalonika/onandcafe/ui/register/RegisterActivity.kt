package com.tessalonika.onandcafe.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.tessalonika.onandcafe.base.enable
import com.tessalonika.onandcafe.base.invalidateAuthForm
import com.tessalonika.onandcafe.base.showToast
import com.tessalonika.onandcafe.base.visible
import com.tessalonika.onandcafe.databinding.ActivityRegisterBinding
import com.tessalonika.onandcafe.model.User
import com.tessalonika.onandcafe.ui.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory(application)
        val viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        binding.apply {
            viewModel.getIsLoading().observe(this@RegisterActivity) {
                pbRegister.visible(it)
                btnRegister.enable(!it)
                btnRegisterLogin.enable(!it)
            }
            viewModel.getOnError().observe(this@RegisterActivity) {
                if (it.isNotEmpty()) showToast(this@RegisterActivity, it, Toast.LENGTH_SHORT)
            }
            viewModel.getIsSuccess().observe(this@RegisterActivity) {
                finish()
            }

            btnRegisterLogin.setOnClickListener {
                Navigation.findNavController(it).popBackStack()
            }

            btnRegister.setOnClickListener {
                val username = tilRegisterUsername.editText?.text.toString()
                val email = tilRegisterEmail.editText?.text.toString()
                val password = tilRegisterPassword.editText?.text.toString()

                if (invalidateAuthForm(this@RegisterActivity, username, email, password)) {
                    viewModel.registerUser(User(0, username, email, password, false))
                }
            }
        }
    }
}