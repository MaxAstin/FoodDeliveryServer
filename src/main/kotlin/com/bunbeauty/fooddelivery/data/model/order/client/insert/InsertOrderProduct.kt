package com.bunbeauty.fooddelivery.data.model.order.client.insert

import java.util.*

data class InsertOrderProduct(
    val menuProductUuid: UUID,
    val count: Int,
)
