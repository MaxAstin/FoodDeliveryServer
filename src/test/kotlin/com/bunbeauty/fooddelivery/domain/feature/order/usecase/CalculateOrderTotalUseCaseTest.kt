package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductTotal
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderTotal
import com.bunbeauty.fooddelivery.fake.FakeOrder
import com.bunbeauty.fooddelivery.fake.FakeOrderProduct
import com.bunbeauty.fooddelivery.fake.FakeOrderProductAddition
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateOrderTotalUseCaseTest {

    private lateinit var calculateOrderTotalUseCase: CalculateOrderTotalUseCase

    @Before
    fun setUp() {
        calculateOrderTotalUseCase = CalculateOrderTotalUseCase()
    }

    @Test
    fun `returns calculated order total when no delivery and no discount`() {
        val order = FakeOrder.create(
            isDelivery = false,
            deliveryCost = null,
            percentDiscount = null,
            orderProductList = listOf(
                FakeOrderProduct.create(
                    uuid = "1",
                    count = 1,
                    newPrice = 100,
                    oldPrice = 120
                ),
                FakeOrderProduct.create(
                    uuid = "2",
                    count = 2,
                    newPrice = 200,
                    oldPrice = 215
                ),
                FakeOrderProduct.create(
                    uuid = "3",
                    count = 3,
                    newPrice = 100,
                    oldPrice = null
                ),
            ),
        )
        val expected = OrderTotal(
            newTotalCost = 800,
            oldTotalCost = 850,
            productTotalMap = mapOf(
                "1" to OrderProductTotal(
                    additionsPrice = null,
                    newTotalCost = 100,
                    oldTotalCost = 120,
                ),
                "2" to OrderProductTotal(
                    additionsPrice = null,
                    newTotalCost = 400,
                    oldTotalCost = 430,
                ),
                "3" to OrderProductTotal(
                    additionsPrice = null,
                    newTotalCost = 300,
                    oldTotalCost = null,
                ),
            ),
        )

        val result = calculateOrderTotalUseCase(order)

        assertEquals(expected, result)
    }

    @Test
    fun `returns calculated order total when there is delivery and discount`() {
        val order = FakeOrder.create(
            isDelivery = true,
            deliveryCost = 100,
            percentDiscount = 10,
            orderProductList = listOf(
                FakeOrderProduct.create(
                    uuid = "1",
                    count = 1,
                    newPrice = 100,
                    oldPrice = 120
                ),
                FakeOrderProduct.create(
                    uuid = "2",
                    count = 2,
                    newPrice = 200,
                    oldPrice = 215
                ),
                FakeOrderProduct.create(
                    uuid = "3",
                    count = 3,
                    newPrice = 100,
                    oldPrice = null
                ),
            ),
        )
        val expected = OrderTotal(
            newTotalCost = 820,
            oldTotalCost = 950,
            productTotalMap = mapOf(
                "1" to OrderProductTotal(
                    additionsPrice = null,
                    newTotalCost = 100,
                    oldTotalCost = 120,
                ),
                "2" to OrderProductTotal(
                    additionsPrice = null,
                    newTotalCost = 400,
                    oldTotalCost = 430,
                ),
                "3" to OrderProductTotal(
                    additionsPrice = null,
                    newTotalCost = 300,
                    oldTotalCost = null,
                ),
            ),
        )

        val result = calculateOrderTotalUseCase(order)

        assertEquals(expected, result)
    }

    @Test
    fun `returns calculated order total when there are additions`() {
        val order = FakeOrder.create(
            isDelivery = false,
            deliveryCost = null,
            percentDiscount = null,
            orderProductList = listOf(
                FakeOrderProduct.create(
                    uuid = "1",
                    count = 1,
                    newPrice = 100,
                    oldPrice = 120,
                    additions = listOf(
                        FakeOrderProductAddition.create(price = null)
                    )
                ),
                FakeOrderProduct.create(
                    uuid = "2",
                    count = 2,
                    newPrice = 200,
                    oldPrice = 215,
                    additions = listOf(
                        FakeOrderProductAddition.create(price = 50)
                    )
                ),
                FakeOrderProduct.create(
                    uuid = "3",
                    count = 3,
                    newPrice = 100,
                    oldPrice = null,
                    additions = listOf(
                        FakeOrderProductAddition.create(price = 20),
                        FakeOrderProductAddition.create(price = 30),
                    )
                ),
            ),
        )
        val expected = OrderTotal(
            newTotalCost = 1050,
            oldTotalCost = 1100,
            productTotalMap = mapOf(
                "1" to OrderProductTotal(
                    additionsPrice = null,
                    newTotalCost = 100,
                    oldTotalCost = 120,
                ),
                "2" to OrderProductTotal(
                    additionsPrice = 50,
                    newTotalCost = 500,
                    oldTotalCost = 530,
                ),
                "3" to OrderProductTotal(
                    additionsPrice = 50,
                    newTotalCost = 450,
                    oldTotalCost = null,
                ),
            ),
        )

        val result = calculateOrderTotalUseCase(order)

        assertEquals(expected, result)
    }

    @Test
    fun `returns calculated order total when there is delivery, discount and additions`() {
        val order = FakeOrder.create(
            isDelivery = true,
            deliveryCost = 100,
            percentDiscount = 10,
            orderProductList = listOf(
                FakeOrderProduct.create(
                    uuid = "1",
                    count = 1,
                    newPrice = 100,
                    oldPrice = 120,
                    additions = listOf(
                        FakeOrderProductAddition.create(price = null)
                    )
                ),
                FakeOrderProduct.create(
                    uuid = "2",
                    count = 2,
                    newPrice = 200,
                    oldPrice = 215,
                    additions = listOf(
                        FakeOrderProductAddition.create(price = 50)
                    )
                ),
                FakeOrderProduct.create(
                    uuid = "3",
                    count = 3,
                    newPrice = 100,
                    oldPrice = null,
                    additions = listOf(
                        FakeOrderProductAddition.create(price = 20),
                        FakeOrderProductAddition.create(price = 30),
                    )
                ),
            ),
        )
        val expected = OrderTotal(
            newTotalCost = 1045,
            oldTotalCost = 1200,
            productTotalMap = mapOf(
                "1" to OrderProductTotal(
                    additionsPrice = null,
                    newTotalCost = 100,
                    oldTotalCost = 120,
                ),
                "2" to OrderProductTotal(
                    additionsPrice = 50,
                    newTotalCost = 500,
                    oldTotalCost = 530,
                ),
                "3" to OrderProductTotal(
                    additionsPrice = 50,
                    newTotalCost = 450,
                    oldTotalCost = null,
                ),
            ),
        )

        val result = calculateOrderTotalUseCase(order)

        assertEquals(expected, result)
    }
}