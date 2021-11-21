package com.bunbeauty.food_delivery.data.repo.order

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.model.order.PostOrder
import com.bunbeauty.food_delivery.data.table.OrderTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import java.util.*

class OrderRepository: IOrderRepository {

   override suspend fun insertOrder(postOrder: PostOrder): ResultRow? {
        val insertStatement = query {
             OrderTable.insert { orderTable ->
                orderTable[uuid] = UUID.randomUUID().toString()
                orderTable[isDelivery] = postOrder.isDelivery
                orderTable[code] = "A-00"  //TODO generate code
                orderTable[address] = postOrder.address
                orderTable[comment] = postOrder.comment
                orderTable[deferredTime] = postOrder.deferredTime
                orderTable[status] = "NOT_ACCEPTED"
                orderTable[addressUuid] = postOrder.addressUuid
                orderTable[cafeUuid] = postOrder.cafeUuid ?: "" // TODO get real cafe uuid
                orderTable[userUuid] = UUID.randomUUID().toString() // TODO get real user uuid
            }
        }

        return insertStatement.resultedValues?.firstOrNull()
    }
}