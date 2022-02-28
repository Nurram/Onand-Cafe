package com.tessalonika.onandcafe.ui.login

import androidx.lifecycle.viewModelScope
import com.tessalonika.onandcafe.base.BaseViewModel
import com.tessalonika.onandcafe.db.daos.UserDao
import com.tessalonika.onandcafe.model.User
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userDao: UserDao
): BaseViewModel<User>() {

    fun authenticateUser(value: String, password: String) {
        isLoading.postValue(true)
        viewModelScope.launch {
            val user = userDao.getUserByUsernameEmailAndPassword(value, password)

            if (user != null) {
                isLoading.postValue(false)
                isSuccess.postValue(user)
            } else {
                isLoading.postValue(false)
                onError.postValue("Invalid credentials!")
            }
        }
    }
}