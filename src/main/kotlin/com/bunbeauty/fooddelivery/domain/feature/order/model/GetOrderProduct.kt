package com.bunbeauty.fooddelivery.domain.feature.order.model

import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.GetMenuProduct
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
    val menuProduct: GetMenuProduct,
    val orderUuid: String,
    val additions: List<GetOrderProductAddition>,
)

@Serializable
class GetOrderProductAddition(
    val uuid: String,
    val name: String,
)