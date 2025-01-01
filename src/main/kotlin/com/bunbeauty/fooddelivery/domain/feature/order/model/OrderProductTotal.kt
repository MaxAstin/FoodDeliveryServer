package com.bunbeauty.fooddelivery.domain.feature.order.model

data class OrderProductTotal(
    val additionsPrice: Int?,
    val newCommonPrice: Int,
    val oldCommonPrice: Int?,
    val newTotalCost: Int,
    val oldTotalCost: Int?
)
