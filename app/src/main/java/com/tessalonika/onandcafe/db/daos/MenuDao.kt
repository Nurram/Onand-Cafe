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
    fun insert(menu: Menu)

    @Query("select * from menu")
    fun getAll(): LiveData<List<Menu>>?

    @Query("select * from menu where id=:id")
    fun getById(id: Int): Menu?

    @Delete
    fun delete(menu: Menu)
}