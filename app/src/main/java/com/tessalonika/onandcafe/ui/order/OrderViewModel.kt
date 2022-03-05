package com.tessalonika.onandcafe.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tessalonika.onandcafe.base.BaseViewModel
import com.tessalonika.onandcafe.base.DateUtil
import com.tessalonika.onandcafe.db.daos.OrderWithMenuDao
import com.tessalonika.onandcafe.db.daos.TableDao
import com.tessalonika.onandcafe.model.Order
import com.tessalonika.onandcafe.model.OrderMenuCrossRef
import kotlinx.coroutines.launch

class OrderViewModel(
    private val dao: OrderWithMenuDao?,
    private val tableDao: TableDao?
) : BaseViewModel<Long>() {

    fun insertOrder(value: Order): LiveData<Long> {
        val orderId = MutableLiveData<Long>()

        viewModelScope.launch {
            val table = tableDao?.getTableById(value.tableNo.toInt())

            when {
                table == null -> {
                    onError.postValue("Table number not found!")
                }
                table.isOccupied -> {
                    onError.postValue("Table are already occupied!")
                }
                else -> {
                    val response = dao?.insert(value)

                    if (response != null) {
                        orderId.postValue(response!!)

                        val idToShow = "$response/OC/${DateUtil.formatDate(value.orderDate)}"
                        dao?.setId(idToShow, response)
                    }
                }
            }
        }

        return orderId
    }

    fun setIdToShow(orderId: Long) {

    }

    fun insertOrderWithMenu(orderId: Long, menuId: Long, tableNo: Int) {
        viewModelScope.launch {
            val orderMenuRef = OrderMenuCrossRef(orderId, menuId)
            val id = dao?.insert(orderMenuRef)

            if (id == null) {
                onError.postValue("Cannot save data!")
            } else {
                isSuccess.postValue(id)
                tableDao?.setOccupied(tableNo)
            }
        }
    }
}