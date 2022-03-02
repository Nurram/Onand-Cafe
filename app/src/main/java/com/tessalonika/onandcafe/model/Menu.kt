package com.tessalonika.onandcafe.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "menu")
data class Menu(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val category: String,
    val name: String,
    val description: String,
    val price: Long,

    @Ignore
    var isSelected: Boolean = false
): Parcelable