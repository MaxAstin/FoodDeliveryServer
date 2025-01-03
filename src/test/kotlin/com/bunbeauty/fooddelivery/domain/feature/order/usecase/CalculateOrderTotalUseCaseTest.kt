package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderTotal
import com.bunbeauty.fooddelivery.fake.FakeOrder
import com.bunbeauty.fooddelivery.fake.FakeOrderProduct
import com.bunbeauty.fooddelivery.fake.FakeOrderProductTotal
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateOrderTotalUseCaseTest {

    private val orderProduct1 = FakeOrderProduct.create(uuid = "1")
    private val orderProduct2 = FakeOrderProduct.create(uuid = "2")
    private val orderProductList = listOf(orderProduct1, orderProduct2)
    private val calculateOrderProductsNewCostUseCase: CalculateOrderProductsNewCostUseCase = mockk {
        every {
            this@mockk(
                orderProductList = orderProductList,
                percentDiscount = null
            )
        } returns 500
    }
    private val calculateOrderProductsOldCostUseCase: CalculateOrderProductsOldCostUseCase = mockk {
        every {
            this@mockk(
                orderProductList = orderProductList,
                percentDiscount = null
            )
        } returns 600
    }
    private val orderProductTotal1 = FakeOrderProductTotal.create()
    private val orderProductTotal2 = FakeOrderProductTotal.create()
    private val calculateOrderProductTotalUseCase: CalculateOrderProductTotalUseCase = mockk {
        every { this@mockk(orderProduct1) } returns orderProductTotal1
        every { this@mockk(orderProduct2) } returns orderProductTotal2
    }

    private lateinit var calculateOrderTotalUseCase: CalculateOrderTotalUseCase

    @Before
    fun setUp() {
        calculateOrderTotalUseCase = CalculateOrderTotalUseCase(
            calculateOrderProductsNewCostUseCase = calculateOrderProductsNewCostUseCase,
            calculateOrderProductsOldCostUseCase = calculateOrderProductsOldCostUseCase,
            calculateOrderProductTotalUseCase = calculateOrderProductTotalUseCase
        )
    }

    @Test
    fun `returns order total when no delivery`() {
        val order = FakeOrder.create(
            isDelivery = false,
            deliveryCost = null,
            orderProductList = listOf(
                orderProduct1,
                orderProduct2
            )
        )
        val expected = OrderTotal(
            newTotalCost = 500,
            oldTotalCost = 600,
            productTotalMap = mapOf(
                "1" to orderProductTotal1,
                "2" to orderProductTotal2
            )
        )

        val result = calculateOrderTotalUseCase(order)

        assertEquals(expected, result)
    }

    @Test
    fun `returns order total when delivery is not null`() {
        val order = FakeOrder.create(
            isDelivery = true,
            deliveryCost = 100,
            orderProductList = listOf(
                orderProduct1,
                orderProduct2
            )
        )
        val expected = OrderTotal(
            newTotalCost = 600,
            oldTotalCost = 700,
            productTotalMap = mapOf(
                "1" to orderProductTotal1,
                "2" to orderProductTotal2
            )
        )

        val result = calculateOrderTotalUseCase(order)

        assertEquals(expected, result)
    }
}
