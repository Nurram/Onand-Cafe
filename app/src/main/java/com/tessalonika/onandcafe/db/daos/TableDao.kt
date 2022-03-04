package com.tessalonika.onandcafe.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tessalonika.onandcafe.model.Table

@Dao
interface TableDao {

    @Insert
    suspend fun insertTable(table: Table): Long

    @Insert
    suspend fun insertTables(tables: List<Table>)

    @Query("select * from `table`")
    fun getTables(): LiveData<List<Table>>?

    @Query("update `table` set isOccupied=1 where id=:id")
    suspend fun setOccupied(id: Int)

    @Query("update `table` set isOccupied=0 where id=:id")
    suspend fun setUnOccupied(id: Int)

    @Query("select isOccupied from `table` where id=:id")
    suspend fun getIsOccupied(id: Int): Int
}