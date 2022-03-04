package com.tessalonika.onandcafe.model

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Junction
import androidx.room.Relation

data class OrderWithMenu(
    @Embedded
    val order: Order,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "menuId",
        associateBy = Junction(OrderMenuCrossRef::class)
    )
    val menus: List<Menu>,

    @Ignore
    val type: Int = 1
)