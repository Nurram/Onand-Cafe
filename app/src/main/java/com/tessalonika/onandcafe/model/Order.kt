package com.tessalonika.onandcafe.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tessalonika.onandcafe.db.converter.DateConverter
import java.util.*

@Entity(tableName = "order")
@TypeConverters(DateConverter::class)
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int,
    val paymentType: String,
    val orderDate: Date,
    val totalPrice: Long,
    val tableNo: String
)