package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.domain.feature.notification.SendPickupClientNotificationUseCase
import com.bunbeauty.fooddelivery.fake.FakeClientUserLight
import com.bunbeauty.fooddelivery.fake.FakeOrder
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class UpdateOrderStatusUseCaseTest {

    private val orderRepository: OrderRepository = mockk()
    private val sendPickupClientNotificationUseCase: SendPickupClientNotificationUseCase = mockk()
    private val useCase: UpdateOrderStatusUseCase =
        UpdateOrderStatusUseCase(orderRepository, sendPickupClientNotificationUseCase)

    @Test
    fun `invoke should update order status and return order`() = runTest {
        // Arrange
        val orderUuid = "order123"
        val status = "PROCESSING"
        val expectedOrder = FakeOrder.create(
            uuid = orderUuid,
            status = status,
            clientUser = FakeClientUserLight.create(
                uuid = "client123"
            ),
            code = "ORDER-001"
        )

        coEvery { orderRepository.updateOrderStatusByUuid(orderUuid, status) } returns expectedOrder
        coEvery { sendPickupClientNotificationUseCase(any(), any()) } returns Unit

        // Act
        val result = useCase(orderUuid, status)

        // Assert
        assertEquals(expectedOrder, result)
        coVerify(exactly = 0) { sendPickupClientNotificationUseCase(any(), any()) }
    }

    @Test
    fun `invoke should send notification when status is DONE`() = runTest {
        // Arrange
        val orderUuid = "order123"
        val status = "DONE"
        val expectedOrder = FakeOrder.create(
            uuid = orderUuid,
            status = status,
            clientUser = FakeClientUserLight.create(uuid = "client123"),
            code = "ORDER-001"
        )

        coEvery { orderRepository.updateOrderStatusByUuid(orderUuid, status) } returns expectedOrder
        coEvery { sendPickupClientNotificationUseCase("client123", "ORDER-001") } returns Unit

        // Act
        val result = useCase(orderUuid, status)

        // Assert
        assertEquals(expectedOrder, result)
        coVerify(exactly = 1) { sendPickupClientNotificationUseCase("client123", "ORDER-001") }
    }

    @Test
    fun `invoke should not send notification for non-DONE statuses`() = runTest {
        // Arrange
        val orderUuid = "order123"
        val status = "CANCELLED"
        val expectedOrder = FakeOrder.create(
            uuid = orderUuid,
            status = status,
            clientUser = FakeClientUserLight.create(uuid = "client123"),
            code = "ORDER-001"
        )

        coEvery { orderRepository.updateOrderStatusByUuid(orderUuid, status) } returns expectedOrder

        // Act
        val result = useCase(orderUuid, status)

        // Assert
        assertEquals(expectedOrder, result)
        coVerify(exactly = 0) { sendPickupClientNotificationUseCase(any(), any()) }
    }
}
