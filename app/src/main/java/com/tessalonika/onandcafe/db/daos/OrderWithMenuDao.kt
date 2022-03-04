package com.tessalonika.onandcafe.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tessalonika.onandcafe.model.Order
import com.tessalonika.onandcafe.model.OrderMenuCrossRef
import com.tessalonika.onandcafe.model.OrderWithMenu

@Dao
interface OrderWithMenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(value: Order): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(value: OrderMenuCrossRef): Int

    @Transaction
    @Query("select * from `order`")
    fun getAll(): List<OrderWithMenu>
}