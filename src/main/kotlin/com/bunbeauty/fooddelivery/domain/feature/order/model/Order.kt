package com.bunbeauty.fooddelivery.domain.feature.order.model

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithCity
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserLight
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.MenuProduct

class Order(
    val uuid: String,
    val time: Long,
    val isDelivery: Boolean,
    val code: String,
    val addressDescription: String?,
    val addressStreet: String?,
    val addressHouse: String?,
    val addressFlat: String?,
    val addressEntrance: String?,
    val addressFloor: String?,
    val addressComment: String?,
    val comment: String?,
    val deferredTime: Long?,
    val status: String,
    val deliveryCost: Int?,
    val paymentMethod: String?,
    val percentDiscount: Int?,
    val cafeWithCity: CafeWithCity,
    val company: Company,
    val clientUser: ClientUserLight,
    val orderProducts: List<OrderProduct>,
)

class OrderProduct(
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
    val menuProduct: MenuProduct,
    val additions: List<OrderProductAddition>,
)

class OrderProductAddition(
    val uuid: String,
    val name: String,
    val price: Int?,
    val priority: Int,
)