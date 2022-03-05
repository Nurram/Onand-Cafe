package com.tessalonika.onandcafe.ui.menu

import androidx.lifecycle.viewModelScope
import com.tessalonika.onandcafe.base.BaseViewModel
import com.tessalonika.onandcafe.base.PrefUtils
import com.tessalonika.onandcafe.db.daos.MenuDao
import com.tessalonika.onandcafe.model.Menu
import com.tessalonika.onandcafe.model.User
import kotlinx.coroutines.launch

class MenuViewModel(
    private val dao: MenuDao?,
    private val prefUtils: PrefUtils
) : BaseViewModel<User>() {

    fun getAll() = dao?.getAll()

    fun getAllNonCoffee() = dao?.getAllNonCoffee()

    fun insert(data: Menu) {
        viewModelScope.launch {
            dao?.insert(data)
        }
    }

    fun delete(data: Menu) {
        viewModelScope.launch {
            dao?.delete(data)
        }
    }

    fun getIsAdmin(): Boolean = prefUtils.getFromPrefBoolean(PrefUtils.AUTH_ADMIN_PREF)
}