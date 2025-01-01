package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductTotal
import com.bunbeauty.fooddelivery.fake.FakeOrderProduct
import com.bunbeauty.fooddelivery.fake.FakeOrderProductAddition
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

class CalculateOrderProductTotalUseCaseTest {

    private lateinit var calculateOrderProductTotalUseCase: CalculateOrderProductTotalUseCase

    @BeforeTest
    fun setup() {
        calculateOrderProductTotalUseCase = CalculateOrderProductTotalUseCase()
    }

    @Test
    fun `return total when count is 1`() {
        val orderProduct = FakeOrderProduct.create(
            uuid = "1",
            count = 1,
            newPrice = 100,
            oldPrice = null
        )
        val expected = OrderProductTotal(
            additionsPrice = null,
            newCommonPrice = 100,
            oldCommonPrice = null,
            newTotalCost = 100,
            oldTotalCost = null
        )

        val total = calculateOrderProductTotalUseCase(orderProduct)

        assertEquals(expected, total)
    }

    @Test
    fun `return total when count is more then 1`() {
        val orderProduct = FakeOrderProduct.create(
            uuid = "1",
            count = 2,
            newPrice = 100,
            oldPrice = null
        )
        val expected = OrderProductTotal(
            additionsPrice = null,
            newCommonPrice = 100,
            oldCommonPrice = null,
            newTotalCost = 200,
            oldTotalCost = null
        )

        val total = calculateOrderProductTotalUseCase(orderProduct)

        assertEquals(expected, total)
    }

    @Test
    fun `return total when count is more then 1 and has old price`() {
        val orderProduct = FakeOrderProduct.create(
            uuid = "1",
            count = 2,
            newPrice = 100,
            oldPrice = 200
        )
        val expected = OrderProductTotal(
            additionsPrice = null,
            newCommonPrice = 100,
            oldCommonPrice = 200,
            newTotalCost = 200,
            oldTotalCost = 400
        )

        val total = calculateOrderProductTotalUseCase(orderProduct)

        assertEquals(expected, total)
    }

    @Test
    fun `return total when count is more then 1 and has additions`() {
        val orderProduct = FakeOrderProduct.create(
            uuid = "1",
            count = 2,
            newPrice = 100,
            oldPrice = 200,
            additions = listOf(
                FakeOrderProductAddition.create(10),
                FakeOrderProductAddition.create(20)
            )
        )
        val expected = OrderProductTotal(
            additionsPrice = 30,
            newCommonPrice = 130,
            oldCommonPrice = 230,
            newTotalCost = 260,
            oldTotalCost = 460
        )

        val total = calculateOrderProductTotalUseCase(orderProduct)

        assertEquals(expected, total)
    }
}
