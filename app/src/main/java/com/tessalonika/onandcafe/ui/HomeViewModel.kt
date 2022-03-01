package com.tessalonika.onandcafe.ui

import androidx.lifecycle.viewModelScope
import com.tessalonika.onandcafe.base.BaseViewModel
import com.tessalonika.onandcafe.base.PrefUtils
import com.tessalonika.onandcafe.db.daos.UserDao
import com.tessalonika.onandcafe.model.User
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userDao: UserDao?,
    private val prefUtils: PrefUtils
): BaseViewModel<User>() {

    fun getUserById() {
        val id = prefUtils.getFromPrefInt(PrefUtils.AUTH_ID_PREF)

        viewModelScope.launch { isSuccess.postValue(userDao?.getUserById(id.toString())) }
    }
}