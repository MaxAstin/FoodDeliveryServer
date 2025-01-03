package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.domain.error.errorWithCode
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct

private const val ORDER_COST_IS_LOWER_THEN_MINIMAL_CODE = 900

class GetDeliveryCostUseCase(
    private val clientUserRepository: ClientUserRepository,
    private val calculateOrderProductsNewCostUseCase: CalculateOrderProductsNewCostUseCase
) {

    suspend operator fun invoke(
        isDelivery: Boolean,
        deliveryZone: DeliveryZone?,
        clientUserUuid: String,
        orderProducts: List<OrderProduct>,
        percentDiscount: Int?
    ): Int? {
        if (!isDelivery) {
            return null
        }

        val orderProductsNewCost = calculateOrderProductsNewCostUseCase(
            orderProductList = orderProducts,
            percentDiscount = percentDiscount
        )

        return if (deliveryZone == null) {
            val company = clientUserRepository.getClientUserByUuid(uuid = clientUserUuid)
                .orThrowNotFoundByUuidError(clientUserUuid)
                .company
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
