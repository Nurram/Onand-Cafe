package com.tessalonika.onandcafe.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tessalonika.onandcafe.R
import com.tessalonika.onandcafe.base.BaseViewModel
import com.tessalonika.onandcafe.db.daos.UserDao
import com.tessalonika.onandcafe.model.User
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDao: UserDao?
): BaseViewModel<Long>() {

    fun registerUser(user: User) {
        isLoading.postValue(true)
        viewModelScope.launch {
            val isEmailExist = userDao?.getUserCountByUsernameEmail(user.email)
            val isUsernameExist = userDao?.getUserCountByUsernameEmail(user.username)

            if (isEmailExist != null && isUsernameExist != null) {
                if (isEmailExist > 0 || isUsernameExist > 0) {
                    onError.postValue("Email or username are used!")
                    isLoading.postValue(false)
                } else {
                    saveUser(user)
                    isLoading.postValue(false)
                }
            }
        }
    }

    private fun saveUser(user: User) {
        viewModelScope.launch {
            isSuccess.postValue(userDao?.insertUser(user))
        }
    }
}