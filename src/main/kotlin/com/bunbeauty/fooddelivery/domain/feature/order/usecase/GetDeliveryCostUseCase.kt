package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct

class GetDeliveryCostUseCase(
    private val clientUserRepository: ClientUserRepository,
    private val calculateOrderProductsNewCostUseCase: CalculateOrderProductsNewCostUseCase,
) {

    suspend operator fun invoke(
        isDelivery: Boolean,
        clientUserUuid: String,
        orderProducts: List<OrderProduct>,
        percentDiscount: Int?,
    ): Int? {
        if (!isDelivery) {
            return null
        }

        val company = clientUserRepository.getClientUserByUuid(uuid = clientUserUuid)
            .orThrowNotFoundByUuidError(clientUserUuid)
            .company
        val orderProductsNewCost = calculateOrderProductsNewCostUseCase(
            orderProductList = orderProducts,
            percentDiscount = percentDiscount
        )

        return if (orderProductsNewCost >= company.delivery.forFree) {
            0
        } else {
            company.delivery.cost
        }
    }

}