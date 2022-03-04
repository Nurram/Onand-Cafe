package com.tessalonika.onandcafe.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tessalonika.onandcafe.model.Menu

@Dao
interface MenuDao {

    @Insert
    suspend fun insert(menu: Menu)

    @Query("select * from menu where isCoffee=1")
    fun getAll(): LiveData<List<Menu>>?

    @Query("select * from menu where isCoffee=0")
    fun getAllNonCoffee(): LiveData<List<Menu>>?

    @Query("select * from menu where menuId=:id")
    suspend fun getById(id: Int): Menu?

    @Delete
    suspend fun delete(menu: Menu)
}