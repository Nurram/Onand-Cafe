package com.tessalonika.onandcafe.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tessalonika.onandcafe.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User): Long

    @Query("select count(*) from user where username=:value or email=:value")
    suspend fun getUserCountByUsernameEmail(value: String): Int

    @Query("select * from user where (username=:value or email=:value) and password=:password")
    suspend fun getUserByUsernameEmailAndPassword(value: String, password: String): User?
}