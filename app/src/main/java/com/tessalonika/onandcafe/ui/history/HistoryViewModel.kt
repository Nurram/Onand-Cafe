package com.tessalonika.onandcafe.ui.history

import androidx.lifecycle.viewModelScope
import com.tessalonika.onandcafe.base.BaseViewModel
import com.tessalonika.onandcafe.base.DateUtil
import com.tessalonika.onandcafe.db.daos.OrderWithMenuDao
import com.tessalonika.onandcafe.model.Order
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val dao: OrderWithMenuDao?
) : BaseViewModel<List<Order>>() {

    fun getAll() {
        viewModelScope.launch {
            isSuccess.postValue(dao?.getAll())
        }
    }

    fun mapData(orders: ArrayList<Order>): List<Order> =
        if (!orders.isNullOrEmpty()) {
            var date = DateUtil.formatDayDate(orders[0].orderDate)
            val order = Order(orderDate = orders[0].orderDate)
            order.type = 1
            orders.add(0, order)

            var i = 0
            while (i <= orders.size - 1) {
                val formattedDate = DateUtil.formatDayDate(orders[i].orderDate)

                if (date != formattedDate) {
                    date = formattedDate

                    val itemOrder = Order(orderDate = orders[i].orderDate)
                    itemOrder.type = 1
                    orders.add(i, itemOrder)
                } else {
                    i++
                }
            }

            orders
        } else {
            listOf()
        }

}