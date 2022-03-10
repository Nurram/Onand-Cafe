package com.tessalonika.onandcafe.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tessalonika.onandcafe.model.Order
import com.tessalonika.onandcafe.model.OrderMenuCrossRef
import com.tessalonika.onandcafe.model.OrderWithMenu

@Dao
interface OrderWithMenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: Order): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: OrderMenuCrossRef): Long

    @Transaction
    @Query("select * from `order` order by orderDate desc")
    fun getAll(): LiveData<List<Order>>?

    @Query("update `order` set id=:id where orderId=:orderId")
    suspend fun setId(id: String, orderId: Long)

    @Transaction
    @Query("select * from `order` where orderId=:orderId")
    suspend fun getByOrderId(orderId: Long): OrderWithMenu

    @Transaction
    @Query("update `order` set isPaid=:isPaid where orderId=:orderId")
    suspend fun setPaymentStatus(isPaid: Int, orderId: String)
}