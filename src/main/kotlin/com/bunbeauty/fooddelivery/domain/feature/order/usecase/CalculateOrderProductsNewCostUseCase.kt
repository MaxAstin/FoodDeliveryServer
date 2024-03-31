package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct

class CalculateOrderProductsNewCostUseCase(
    private val calculateOrderProductTotalUseCase: CalculateOrderProductTotalUseCase
) {

    operator fun invoke(
        orderProductList: List<OrderProduct>,
        percentDiscount: Int?
    ): Int {
        val oderProductsSumCost = orderProductList.sumOf { orderProduct ->
            calculateOrderProductTotalUseCase(orderProduct).newTotalCost
        }
        val discount = (oderProductsSumCost * (percentDiscount ?: 0) / 100.0).toInt()

        return oderProductsSumCost - discount
    }

}