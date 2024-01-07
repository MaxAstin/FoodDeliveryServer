package com.bunbeauty.fooddelivery.domain.feature.order.model

import kotlinx.serialization.Serializable

@Serializable
class GetOrderProduct(
    val uuid: String,
    val count: Int,
    val name: String,
    val newPrice: Int,
    val oldPrice: Int?,
    val utils: String?,
    val nutrition: Int?,
    val description: String,
    val comboDescription: String?,
    val photoLink: String,
    val barcode: Int,
    val orderUuid: String,
    val additionsPrice: Int?,
    val newCommonPrice: Int,
    val oldCommonPrice: Int?,
    val newTotalCost: Int,
    val oldTotalCost: Int?,
    val additions: List<GetOrderProductAddition>,
)

@Serializable
class GetOrderProductAddition(
    val uuid: String,
    val name: String,
)