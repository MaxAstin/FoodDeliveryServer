package com.bunbeauty.fooddelivery.domain.feature.address.model

import kotlinx.serialization.Serializable

@Serializable
class PostAddressV2(
    val street: PostAddressStreet,
    val house: String,
    val flat: String?,
    val entrance: String?,
    val floor: String?,
    val comment: String?,
    val isVisible: Boolean,
    val cityUuid: String
)

@Serializable
class PostAddressStreet(
    val fiasId: String,
    val name: String
)
