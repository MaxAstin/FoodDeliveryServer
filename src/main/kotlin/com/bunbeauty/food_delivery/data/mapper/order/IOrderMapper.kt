package com.bunbeauty.food_delivery.data.mapper.order

import com.bunbeauty.food_delivery.data.model.order.GetOrder
import org.jetbrains.exposed.sql.ResultRow

interface IOrderMapper {

    fun rowToModel(resultRow: ResultRow): GetOrder
}