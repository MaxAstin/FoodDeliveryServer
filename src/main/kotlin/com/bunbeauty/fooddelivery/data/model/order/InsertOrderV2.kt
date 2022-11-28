package com.bunbeauty.fooddelivery.data.model.order

import kotlinx.serialization.Serializable
import java.util.*

data class InsertOrderV2(
    val time: Long,
    val isDelivery: Boolean,
    val code: String,
    val address: InsertOrderAddress,
    val comment: String?,
    val deferredTime: Long?,
    val status: String,
    val deliveryCost: Int?,
    val cafeUuid: UUID,
    val companyUuid: UUID,
    val clientUserUuid: UUID,
    val orderProductList: List<InsertOrderProduct>,
)

@Serializable
class InsertOrderAddress(
    val description: String?,

    val street: String?,
    val house: String?,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?
)