package com.bunbeauty.food_delivery.data.repo.order

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.OrderEntity
import com.bunbeauty.food_delivery.data.model.order.GetOrder
import com.bunbeauty.food_delivery.data.model.order.PostOrder
import com.bunbeauty.food_delivery.data.table.OrderTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import java.util.*

class OrderRepository: IOrderRepository {

   override suspend fun insertOrder(postOrder: PostOrder): GetOrder = query {
       OrderEntity.new {
           isDelivery = postOrder.isDelivery
           code = "A-00"  //TODO generate code
           address = postOrder.address
           comment = postOrder.comment
           deferredTime = postOrder.deferredTime
           status = "NOT_ACCEPTED"
           addressUuid = postOrder.addressUuid
           cafeUuid = postOrder.cafeUuid ?: "" // TODO get real cafe uuid
           userUuid = UUID.randomUUID().toString() // TODO get real user uuid
       }.toOrder()
    }
}