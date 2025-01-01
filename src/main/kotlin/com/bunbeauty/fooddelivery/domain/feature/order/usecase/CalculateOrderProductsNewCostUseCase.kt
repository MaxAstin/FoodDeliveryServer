package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct

class CalculateOrderProductsNewCostUseCase(
    private val calculateOrderProductTotalUseCase: CalculateOrderProductTotalUseCase,
    private val calculateCostWithDiscountUseCase: CalculateCostWithDiscountUseCase,
) {

    operator fun invoke(
        orderProductList: List<OrderProduct>,
        percentDiscount: Int?,
    ): Int {
        val oderProductsSumCost = orderProductList.sumOf { orderProduct ->
            calculateOrderProductTotalUseCase(orderProduct).newTotalCost
        }

        return calculateCostWithDiscountUseCase(
            cost = oderProductsSumCost,
            percentDiscount = percentDiscount
        )
    }

}