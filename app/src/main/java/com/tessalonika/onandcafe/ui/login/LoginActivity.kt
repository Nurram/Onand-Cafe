package com.tessalonika.onandcafe.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tessalonika.onandcafe.base.enable
import com.tessalonika.onandcafe.base.showToast
import com.tessalonika.onandcafe.base.visible
import com.tessalonika.onandcafe.databinding.ActivityLoginBinding
import com.tessalonika.onandcafe.ui.ViewModelFactory
import com.tessalonika.onandcafe.ui.home.HomeActivity
import com.tessalonika.onandcafe.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        if (viewModel.isLoggedIn()) {
            if (viewModel.isAdmin()) {
                moveToHome()
            }
        }

        binding.apply {
            viewModel.getIsLoading().observe(this@LoginActivity) {
                pbLogin.visible(it)
                btnLogin.enable(!it)
                btnLoginRegister.enable(!it)
            }

            viewModel.getOnError().observe(this@LoginActivity) {
                if (it.isNotEmpty()) showToast(this@LoginActivity, it, Toast.LENGTH_SHORT)
            }

            viewModel.getIsSuccess().observe(this@LoginActivity) {
                if (it != null) {
                    if (it.isAdmin) {
                        moveToHome()
                    }
                }
            }

            btnLoginRegister.setOnClickListener {
                val i = Intent(this@LoginActivity, RegisterActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }

            btnLogin.setOnClickListener {
                val username = tilUsername.editText?.text.toString()
                val password = tilPassword.editText?.text.toString()

                viewModel.authenticateUser(username, password)
            }
        }
    }

    private fun moveToHome() {
        val i = Intent(this, HomeActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }
}