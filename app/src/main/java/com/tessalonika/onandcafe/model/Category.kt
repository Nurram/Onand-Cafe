package com.tessalonika.onandcafe.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var categoryName: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var categoryImage: ByteArray,
): Parcelable