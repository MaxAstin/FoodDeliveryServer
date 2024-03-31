package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.fake.FakeOrderProduct
import com.bunbeauty.fooddelivery.fake.FakeOrderProductTotal
import io.mockk.every
import io.mockk.mockk
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateOrderProductsNewCostUseCaseTest {

    private val orderProduct1 = FakeOrderProduct.create(uuid = "1")
    private val orderProductTotal1 = FakeOrderProductTotal.create(newTotalCost = 100)
    private val orderProduct2 = FakeOrderProduct.create(uuid = "2")
    private val orderProductTotal2 = FakeOrderProductTotal.create(newTotalCost = 200)
    private val calculateOrderProductTotalUseCase: CalculateOrderProductTotalUseCase = mockk {
        every {
            this@mockk(orderProduct1)
        } returns orderProductTotal1
        every {
            this@mockk(orderProduct2)
        } returns orderProductTotal2
    }
    private lateinit var calculateOrderProductsNewCostUseCase: CalculateOrderProductsNewCostUseCase

    @BeforeTest
    fun setup() {
        calculateOrderProductsNewCostUseCase = CalculateOrderProductsNewCostUseCase(
            calculateOrderProductTotalUseCase = calculateOrderProductTotalUseCase
        )
    }

    @Test
    fun `return order product sum when discount is not available`() {
        val expected = 300

        val orderProductsNewCost = calculateOrderProductsNewCostUseCase(
            orderProductList = listOf(
                orderProduct1,
                orderProduct2,
            ),
            percentDiscount = null
        )

        assertEquals(expected, orderProductsNewCost)
    }

    @Test
    fun `return order product sum with discount when discount is available`() {
        val expected = 270

        val orderProductsNewCost = calculateOrderProductsNewCostUseCase(
            orderProductList = listOf(
                orderProduct1,
                orderProduct2,
            ),
            percentDiscount = 10
        )

        assertEquals(expected, orderProductsNewCost)
    }

}