package com.tessalonika.onandcafe.ui.history

import androidx.lifecycle.viewModelScope
import com.tessalonika.onandcafe.base.BaseViewModel
import com.tessalonika.onandcafe.db.daos.OrderWithMenuDao
import com.tessalonika.onandcafe.model.OrderMenuCrossRef
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val dao: OrderWithMenuDao?
): BaseViewModel<List<OrderMenuCrossRef>>() {

    fun getAll() {
        viewModelScope.launch {
            isSuccess.postValue(dao?.getAll())
        }
    }
}