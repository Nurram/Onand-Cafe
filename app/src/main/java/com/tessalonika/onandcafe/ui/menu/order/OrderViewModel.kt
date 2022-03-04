package com.tessalonika.onandcafe.ui.menu.order

import androidx.lifecycle.viewModelScope
import com.tessalonika.onandcafe.base.BaseViewModel
import com.tessalonika.onandcafe.db.daos.OrderWithMenuDao
import com.tessalonika.onandcafe.model.Order
import com.tessalonika.onandcafe.model.OrderMenuCrossRef
import com.tessalonika.onandcafe.model.OrderWithMenu
import kotlinx.coroutines.launch

class OrderViewModel(
    private val dao: OrderWithMenuDao?
): BaseViewModel<Int>() {

    fun insertOrder(value: Order, menuId: Int) {
        viewModelScope.launch {
            val orderId = dao?.insert(value)

            if (orderId != null) {
                val orderMenuRef = OrderMenuCrossRef(orderId, menuId)
                val id = dao?.insert(orderMenuRef)

                if (id == null) {
                    onError.postValue("Cannot save data!")
                } else {
                    isSuccess.postValue(id)
                }
            } else {
                onError.postValue("Cannot save data!")
            }
        }
    }
}