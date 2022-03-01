package com.tessalonika.onandcafe.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "table")
data class Table(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    var tableName: String,
    var isOccupied: Boolean = false
)