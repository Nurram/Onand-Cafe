package com.tessalonika.onandcafe.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tessalonika.onandcafe.db.MainDb
import com.tessalonika.onandcafe.ui.login.LoginViewModel
import com.tessalonika.onandcafe.ui.register.RegisterViewModel

class ViewModelFactory(
    private val application: Application
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val db = MainDb.getDb(application)
        val userDao = db!!.userDao

        when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
                return RegisterViewModel(userDao) as T

            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                return LoginViewModel(userDao) as T
        }

        return RegisterViewModel(userDao) as T
    }
}