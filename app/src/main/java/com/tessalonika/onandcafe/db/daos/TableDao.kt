package com.tessalonika.onandcafe.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tessalonika.onandcafe.model.Table
import kotlinx.coroutines.flow.Flow

@Dao
interface TableDao {

    @Insert
    suspend fun insertTable(table: Table): Long

    @Insert
    suspend fun insertTables(tables: List<Table>)

    @Query("select * from `table`")
    suspend fun getTables(): List<Table>?
}