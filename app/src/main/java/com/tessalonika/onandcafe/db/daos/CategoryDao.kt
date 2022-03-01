package com.tessalonika.onandcafe.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tessalonika.onandcafe.model.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category): Long

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("select * from category")
    fun getCategories(): LiveData<List<Category>>

    @Query("select * from category where id=:id")
    suspend fun selectCategoryById(id: String): Category
}