package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.domain.error.errorWithCode
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct

private const val ORDER_COST_IS_LOWER_THEN_MINIMAL_CODE = 900

class GetDeliveryCostUseCase(
    private val calculateOrderProductsNewCostUseCase: CalculateOrderProductsNewCostUseCase
) {

    operator fun invoke(
        isDelivery: Boolean,
        deliveryZone: DeliveryZone?,
        orderProducts: List<OrderProduct>,
        percentDiscount: Int?,
        company: Company
    ): Int? {
        if (!isDelivery) {
            return null
        }

        val orderProductsNewCost = calculateOrderProductsNewCostUseCase(
            orderProductList = orderProducts,
            percentDiscount = percentDiscount
        )

        return if (deliveryZone == null) {
            if (orderProductsNewCost >= company.delivery.forFree) {
                0
            } else {
                company.delivery.cost
            }
        } else {
            if ((deliveryZone.minOrderCost != null) && (orderProductsNewCost < deliveryZone.minOrderCost)) {
                costLowerThemMinimalError(minOrderCost = deliveryZone.minOrderCost)
            }

            if ((deliveryZone.forLowDeliveryCost != null) &&
                (deliveryZone.lowDeliveryCost != null) &&
                (orderProductsNewCost >= deliveryZone.forLowDeliveryCost)
            ) {
                deliveryZone.lowDeliveryCost
            } else {
                deliveryZone.normalDeliveryCost
            }
        }
    }

    private fun costLowerThemMinimalError(minOrderCost: Int): Nothing {
        errorWithCode(
            message = "Order cost is lower then minimal = $minOrderCost",
            code = ORDER_COST_IS_LOWER_THEN_MINIMAL_CODE
        )
    }
}
