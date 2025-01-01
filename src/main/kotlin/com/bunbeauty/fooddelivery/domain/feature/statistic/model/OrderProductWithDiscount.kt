package com.bunbeauty.fooddelivery.domain.feature.statistic.model

import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct

data class OrderProductWithDiscount(
    val orderProduct: OrderProduct,
    val percentDiscount: Int?
)