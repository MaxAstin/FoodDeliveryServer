package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderTotal

class CalculateOrderTotalUseCase {

    // TODO take into account addition price
    operator fun invoke(order: Order): OrderTotal {
        return OrderTotal(
            oldTotalCost = order.oldTotalCost,
            newTotalCost = order.newTotalCost,
        )
    }

    private val Order.newTotalCost: Int
        get() {
            val oderProductsSumCost = oderProducts.sumOf { orderProductEntity ->
                orderProductEntity.count * orderProductEntity.newPrice
            }
            val discount = (oderProductsSumCost * (percentDiscount ?: 0) / 100.0).toInt()

            return oderProductsSumCost - discount + (deliveryCost ?: 0)
        }

    private val Order.oldTotalCost: Int?
        get() {
            val isOldTotalCostEnabled = oderProducts.any { orderProductEntity ->
                orderProductEntity.oldPrice != null
            } || percentDiscount != null
            return if (isOldTotalCostEnabled) {
                oderProducts.sumOf { orderProductEntity ->
                    orderProductEntity.count * (orderProductEntity.oldPrice ?: orderProductEntity.newPrice)
                } + (deliveryCost ?: 0)
            } else {
                null
            }
        }

}