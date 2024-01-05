package com.bunbeauty.fooddelivery.domain.feature.order.model

data class OrderProductTotal(
    val newTotalCost: Int,
    val oldTotalCost: Int?,
)