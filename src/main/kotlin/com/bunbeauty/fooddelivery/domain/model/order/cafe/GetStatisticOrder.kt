package com.bunbeauty.fooddelivery.domain.model.order.cafe

import com.bunbeauty.fooddelivery.domain.model.order.GetStatisticOrderProduct

class GetStatisticOrder(
    val uuid: String,
    val status: String,
    val time: Long,
    val oderProductList: List<GetStatisticOrderProduct>,
)