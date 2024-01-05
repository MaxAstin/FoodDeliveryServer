package com.bunbeauty.fooddelivery.domain.feature.order.model

data class OrderTotal(
    val oldTotalCost: Int?,
    val newTotalCost: Int,
    val productTotalMap: Map<String, OrderProductTotal>,
)