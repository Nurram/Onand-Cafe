package com.tessalonika.onandcafe.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tessalonika.onandcafe.db.converter.DateConverter
import java.util.*

@Entity(tableName = "user")
@TypeConverters(DateConverter::class)
data class User(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    val username: String,
    val email: String,
    val password: String,
    val createdDate: Date = Date(),
    val updatedDate: Date = Date()
)