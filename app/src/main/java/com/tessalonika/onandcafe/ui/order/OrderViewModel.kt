package com.tessalonika.onandcafe.ui.order

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.tessalonika.onandcafe.base.BaseViewModel
import com.tessalonika.onandcafe.db.daos.OrderWithMenuDao
import com.tessalonika.onandcafe.db.daos.TableDao
import com.tessalonika.onandcafe.model.Order
import com.tessalonika.onandcafe.model.OrderMenuCrossRef
import kotlinx.coroutines.launch

class OrderViewModel(
    private val dao: OrderWithMenuDao?,
    private val tableDao: TableDao?
) : BaseViewModel<Long>() {

    fun insertOrder(value: Order, menuId: Long) {
        viewModelScope.launch {
            val tableIsOccupied = tableDao?.getIsOccupied(value.tableNo.toInt()) ?: 0
            Log.d("TAG", "Table $tableIsOccupied")
            if (tableIsOccupied == 1) {
                onError.postValue("Table are already occupied!")
            } else {
                val orderId = dao?.insert(value)

                if (orderId != null) {
                    val orderMenuRef = OrderMenuCrossRef(orderId, menuId)
                    val id = dao?.insert(orderMenuRef)

                    if (id == null) {
                        onError.postValue("Cannot save data!")
                    } else {
                        isSuccess.postValue(id)
                        tableDao?.setOccupied(value.tableNo.toInt())
                    }
                } else {
                    onError.postValue("Cannot save data!")
                }
            }
        }
    }
}