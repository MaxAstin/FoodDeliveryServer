package com.bunbeauty.fooddelivery.data.model.order.cafe

import com.bunbeauty.fooddelivery.data.model.order.GetOrderProduct

class GetStatisticOrder(
    val uuid: String,
    val status: String,
    val time: Long,
    val oderProductList: List<GetOrderProduct>,
)