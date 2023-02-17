package com.bunbeauty.fooddelivery.data.model.statistic

class GetStatisticOrder(
    val uuid: String,
    val code: String,
    val time: Long,
    val statisticOrderProductList: List<GetStatisticOrderProduct>
)