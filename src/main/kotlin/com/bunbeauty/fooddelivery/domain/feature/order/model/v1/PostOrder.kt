package com.bunbeauty.fooddelivery.domain.feature.order.model.v1

import kotlinx.serialization.Serializable

@Serializable
class PostOrder(
    val isDelivery: Boolean,
    val comment: String?,
    val addressDescription: String,
    val deferredTime: Long?,
    val addressUuid: String?,
    val cafeUuid: String?,
    val orderProducts: List<PostOrderProduct>
)

@Serializable
class PostOrderProduct(
    val menuProductUuid: String,
    val count: Int
)
