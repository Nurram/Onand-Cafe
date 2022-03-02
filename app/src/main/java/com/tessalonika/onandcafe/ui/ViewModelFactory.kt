package com.tessalonika.onandcafe.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tessalonika.onandcafe.base.PrefUtils
import com.tessalonika.onandcafe.db.MainDb
import com.tessalonika.onandcafe.ui.login.LoginViewModel
import com.tessalonika.onandcafe.ui.menu.MenuViewModel
import com.tessalonika.onandcafe.ui.register.RegisterViewModel
import com.tessalonika.onandcafe.ui.stock.StockViewModel
import com.tessalonika.onandcafe.ui.table.TableViewModel

class ViewModelFactory(
    private val application: Application
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val db = MainDb.getDb(application)
        val userDao = db?.userDao
        val tableDao = db?.tableDao
        val stockDao = db?.stockDao
        val menuDao = db?.menuDao

        val prefUtil = PrefUtils(application, PrefUtils.AUTH_PREF)

        when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
                return RegisterViewModel(userDao) as T

            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                return LoginViewModel(userDao, prefUtil) as T

            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                return HomeViewModel(userDao, prefUtil) as T

            modelClass.isAssignableFrom(TableViewModel::class.java) ->
                return TableViewModel(tableDao) as T

            modelClass.isAssignableFrom(StockViewModel::class.java) ->
                return StockViewModel(stockDao) as T

            modelClass.isAssignableFrom(MenuViewModel::class.java) ->
                return MenuViewModel(menuDao) as T
        }

        return RegisterViewModel(userDao) as T
    }
}