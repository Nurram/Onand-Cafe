package com.tessalonika.onandcafe.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tessalonika.onandcafe.model.Stock

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: Stock): Long

    @Delete
    suspend fun deleteStock(stock: Stock)

    @Query("select * from stock")
    fun getStocks(): LiveData<List<Stock>>

    @Query("select * from stock where id=:id")
    suspend fun getStockById(id: Int): Stock
}