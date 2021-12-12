package com.bunbeauty.food_delivery.data.model.order

import java.util.*

data class InsertOrderProduct(
    val menuProductUuid: UUID,
    val count: Int,
)
