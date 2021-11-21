package com.bunbeauty.food_delivery.data.mapper.order

import com.bunbeauty.food_delivery.data.model.order.GetOrder
import com.bunbeauty.food_delivery.data.table.OrderTable
import org.jetbrains.exposed.sql.ResultRow

class OrderMapper: IOrderMapper {

    override fun rowToModel(resultRow: ResultRow): GetOrder {
        return GetOrder(
            uuid = resultRow[OrderTable.uuid],
            isDelivery = resultRow[OrderTable.isDelivery],
            code = resultRow[OrderTable.code],
            address = resultRow[OrderTable.address],
            comment = resultRow[OrderTable.comment],
            deferredTime = resultRow[OrderTable.deferredTime],
            status = resultRow[OrderTable.status],
            addressUuid = resultRow[OrderTable.addressUuid],
            cafeUuid = resultRow[OrderTable.cafeUuid],
            userUuid = resultRow[OrderTable.userUuid],
        )
    }
}