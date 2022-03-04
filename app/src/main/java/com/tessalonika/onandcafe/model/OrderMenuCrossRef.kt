package com.tessalonika.onandcafe.model

import androidx.room.Entity

@Entity(primaryKeys = ["orderId", "menuId"])
data class OrderMenuCrossRef(
    val orderId: Long,
    val menuId: Long
)
