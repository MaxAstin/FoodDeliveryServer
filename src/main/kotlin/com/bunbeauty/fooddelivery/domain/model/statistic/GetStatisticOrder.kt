package com.bunbeauty.fooddelivery.domain.model.statistic

class GetStatisticOrder(
    val uuid: String,
    val code: String,
    val time: Long,
    val statisticOrderProductList: List<GetStatisticOrderProduct>
)
