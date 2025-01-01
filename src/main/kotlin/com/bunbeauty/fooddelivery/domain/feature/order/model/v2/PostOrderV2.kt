package com.bunbeauty.fooddelivery.domain.feature.order.model.v2

import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.PostOrderProduct
import kotlinx.serialization.Serializable

@Serializable
class PostOrderV2(
    val isDelivery: Boolean,
    val address: PostOrderAddressV2,
    val comment: String?,
    val deferredTime: Long?,
    val paymentMethod: String? = null,
    val orderProducts: List<PostOrderProduct>
)

@Serializable
class PostOrderAddressV2(
    val uuid: String,
    val description: String?,

    val street: String?,
    val house: String?,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?
)
