package com.tessalonika.onandcafe.db.daos

import androidx.room.*
import com.tessalonika.onandcafe.model.Order
import com.tessalonika.onandcafe.model.OrderMenuCrossRef

@Dao
interface OrderWithMenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: Order): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: OrderMenuCrossRef): Long

    @Transaction
    @Query("select * from `order` order by orderDate desc")
    suspend fun getAll(): List<Order>

    @Query("update `order` set id=:id where orderId=:orderId")
    suspend fun setId(id: String, orderId: Long)
}