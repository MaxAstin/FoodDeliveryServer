package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct

class CalculateOrderProductsOldCostUseCase(
    private val calculateOrderProductTotalUseCase: CalculateOrderProductTotalUseCase
) {

    operator fun invoke(
        orderProductList: List<OrderProduct>,
        percentDiscount: Int?
    ): Int? {
        val isOldTotalCostEnabled = orderProductList.any { orderProduct ->
            calculateOrderProductTotalUseCase(orderProduct).oldTotalCost != null
        } || percentDiscount != null

        return if (isOldTotalCostEnabled) {
            orderProductList.sumOf { orderProduct ->
                val productTotal = calculateOrderProductTotalUseCase(orderProduct)
                productTotal.oldTotalCost ?: productTotal.newTotalCost
            }
        } else {
            null
        }
    }
}
