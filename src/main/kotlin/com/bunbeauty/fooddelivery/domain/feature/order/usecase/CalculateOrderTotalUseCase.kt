package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderTotal

class CalculateOrderTotalUseCase(
    private val calculateOrderProductsNewCostUseCase: CalculateOrderProductsNewCostUseCase,
    private val calculateOrderProductsOldCostUseCase: CalculateOrderProductsOldCostUseCase,
    private val calculateOrderProductTotalUseCase: CalculateOrderProductTotalUseCase,
) {

    operator fun invoke(order: Order): OrderTotal {
        val oldCost = calculateOrderProductsOldCostUseCase(
            orderProductList = order.oderProducts,
            percentDiscount = order.percentDiscount
        )
        val newCost = calculateOrderProductsNewCostUseCase(
            orderProductList = order.oderProducts,
            percentDiscount = order.percentDiscount
        )

        return OrderTotal(
            oldTotalCost = oldCost?.let {
                oldCost + (order.deliveryCost ?: 0)
            },
            newTotalCost = newCost + (order.deliveryCost ?: 0),
            productTotalMap = order.oderProducts.associate { orderProduct ->
                orderProduct.uuid to calculateOrderProductTotalUseCase(orderProduct)
            }
        )
    }

}