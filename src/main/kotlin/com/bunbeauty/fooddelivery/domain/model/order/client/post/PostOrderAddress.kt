package com.bunbeauty.fooddelivery.domain.model.order.client.post

import kotlinx.serialization.Serializable

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