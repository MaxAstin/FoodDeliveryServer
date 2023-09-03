package com.bunbeauty.fooddelivery.data.model.company

import java.util.UUID

class UpdateCompany(
    val uuid: UUID,
    val name: String? = null,
    val forFreeDelivery: Int? = null,
    val deliveryCost: Int? = null,
    val forceUpdateVersion: Int? = null,
    val percentDiscount: Int? = null,
)
