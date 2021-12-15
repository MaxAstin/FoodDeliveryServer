package com.bunbeauty.fooddelivery.data.model.order

import java.util.*

data class InsertOrderProduct(
    val menuProductUuid: UUID,
    val count: Int,
)
