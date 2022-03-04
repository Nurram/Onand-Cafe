package com.tessalonika.onandcafe.ui.stock

import androidx.lifecycle.viewModelScope
import com.tessalonika.onandcafe.base.BaseViewModel
import com.tessalonika.onandcafe.db.daos.StockDao
import com.tessalonika.onandcafe.model.Stock
import kotlinx.coroutines.launch

class StockViewModel(
    private val stockDao: StockDao?
) : BaseViewModel<List<Stock>>() {

    fun getStocks() = stockDao?.getStocks()

    fun insertStock(stock: Stock) {
        viewModelScope.launch {
            val existingStock = stockDao?.getStockById(stock.id)

            if (existingStock != null) {
                onError.postValue("ID already used!")
            } else {
                stockDao?.insertStock(stock)
            }
        }
    }

    fun deleteStock(stock: Stock) {
        viewModelScope.launch {
            stockDao?.deleteStock(stock)
        }
    }
}