package com.bunbeauty.fooddelivery.data.model.order

import kotlinx.serialization.Serializable

@Serializable
class PostOrderV2(
    val isDelivery: Boolean,
    val address: PostOrderAddress,
    val comment: String?,
    val deferredTime: Long?,
    val orderProducts: List<PostOrderProduct>,
)

@Serializable
class PostOrderAddress(
    val uuid: String,
    val description: String?,

    val street: String?,
    val house: String?,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?
)