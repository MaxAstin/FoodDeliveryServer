package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.notification.SendPickupClientNotificationUseCase
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order

class UpdateOrderStatusUseCase(
    private val orderRepository: OrderRepository,
    private val sendPickupClientNotificationUseCase: SendPickupClientNotificationUseCase
) {
    suspend operator fun invoke(
        orderUuid: String,
        status: String
    ): Order {
        val order = orderRepository.updateOrderStatusByUuid(
            orderUuid = orderUuid,
            status = status
        ).orThrowNotFoundByUuidError(orderUuid)

        if (order.status == OrderStatus.DONE.name) {
            sendPickupClientNotificationUseCase(
                clientUuid = order.clientUser.uuid,
                orderCode = order.code
            )
        }

        return order
    }
}
