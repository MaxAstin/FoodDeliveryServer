package com.bunbeauty.fooddelivery.domain.feature.order.model.v3

import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.PostOrderAddressV2
import kotlinx.serialization.Serializable

@Serializable
class PostOrderV3(
    val isDelivery: Boolean,
    val address: PostOrderAddressV2,
    val comment: String?,
    val deferredTime: Long?,
    val paymentMethod: String? = null,
    val orderProducts: List<PostOrderProductV3>
)

@Serializable
class PostOrderProductV3(
    val menuProductUuid: String,
    val count: Int,
    val additionUuids: List<String>
)
