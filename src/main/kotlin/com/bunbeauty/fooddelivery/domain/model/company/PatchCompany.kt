package com.bunbeauty.fooddelivery.domain.model.company

import kotlinx.serialization.Serializable

@Serializable
class PatchCompany(
    val name: String? = null,
    val forFreeDelivery: Int? = null,
    val deliveryCost: Int? = null,
    val forceUpdateVersion: Int? = null,
    val percentDiscount: Int? = null,
    val isOpen: Boolean? = null,
)
