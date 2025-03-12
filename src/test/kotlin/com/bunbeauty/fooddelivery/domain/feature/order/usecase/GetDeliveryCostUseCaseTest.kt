package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.domain.error.ExceptionWithCode
import com.bunbeauty.fooddelivery.fake.FakeCompany
import com.bunbeauty.fooddelivery.fake.FakeDeliveryZone
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class GetDeliveryCostUseCaseTest {

    private val calculateOrderProductsNewCostUseCase: CalculateOrderProductsNewCostUseCase = mockk()
    private lateinit var getDeliveryCostUseCase: GetDeliveryCostUseCase

    @BeforeTest
    fun setup() {
        getDeliveryCostUseCase = GetDeliveryCostUseCase(
            calculateOrderProductsNewCostUseCase = calculateOrderProductsNewCostUseCase
        )
    }

    @Test
    fun `return null when not delivery`() = runTest {
        val deliveryCost = getDeliveryCostUseCase(
            isDelivery = false,
            deliveryZone = null,
            orderProducts = emptyList(),
            percentDiscount = null,
            company = FakeCompany.create(
                forFreeDelivery = 500,
                deliveryCost = 100
            )
        )

        assertNull(deliveryCost)
    }

    @Test
    fun `return zero when delivery zone is null and products cost is more than necessary for free delivery`() =
        runTest {
            val productsCost = 600
            coEvery {
                calculateOrderProductsNewCostUseCase(any(), any())
            } returns productsCost
            val expected = 0

            val deliveryCost = getDeliveryCostUseCase(
                isDelivery = true,
                deliveryZone = null,
                orderProducts = emptyList(),
                percentDiscount = null,
                company = FakeCompany.create(
                    forFreeDelivery = 500,
                    deliveryCost = 100
                )
            )

            assertEquals(expected, deliveryCost)
        }

    @Test
    fun `return not zero when delivery zone is null and products cost is less than necessary for free delivery`() =
        runTest {
            val productsCost = 200
            coEvery {
                calculateOrderProductsNewCostUseCase(any(), any())
            } returns productsCost
            val expected = 100

            val deliveryCost = getDeliveryCostUseCase(
                isDelivery = true,
                deliveryZone = null,
                orderProducts = emptyList(),
                percentDiscount = null,
                company = FakeCompany.create(
                    forFreeDelivery = 500,
                    deliveryCost = 100
                )
            )

            assertEquals(expected, deliveryCost)
        }

    @Test
    fun `throw error when products cost is less than minimal`() = runTest {
        val productsCost = 200
        coEvery {
            calculateOrderProductsNewCostUseCase(any(), any())
        } returns productsCost
        val deliveryZone = FakeDeliveryZone.create(minOrderCost = 300)

        assertFailsWith(
            exceptionClass = ExceptionWithCode::class,
            message = "Order cost is lower then minimal = 300",
            block = {
                getDeliveryCostUseCase(
                    isDelivery = true,
                    deliveryZone = deliveryZone,
                    orderProducts = emptyList(),
                    percentDiscount = null,
                    company = FakeCompany.create(
                        forFreeDelivery = 500,
                        deliveryCost = 100
                    )
                )
            }
        )
    }

    @Test
    fun `return normal delivery cost when product cost is less than necessary for low delivery`() = runTest {
        val productsCost = 500
        coEvery {
            calculateOrderProductsNewCostUseCase(any(), any())
        } returns productsCost
        val deliveryZone = FakeDeliveryZone.create(
            normalDeliveryCost = 200,
            forLowDeliveryCost = 1000,
            lowDeliveryCost = 100
        )
        val expected = 200

        val deliveryCost = getDeliveryCostUseCase(
            isDelivery = true,
            deliveryZone = deliveryZone,
            orderProducts = emptyList(),
            percentDiscount = null,
            company = FakeCompany.create(
                forFreeDelivery = 500,
                deliveryCost = 100
            )
        )

        assertEquals(expected, deliveryCost)
    }

    @Test
    fun `return low delivery cost when product cost is more than necessary for low delivery`() = runTest {
        val productsCost = 1200
        coEvery {
            calculateOrderProductsNewCostUseCase(any(), any())
        } returns productsCost
        val deliveryZone = FakeDeliveryZone.create(
            normalDeliveryCost = 200,
            forLowDeliveryCost = 1000,
            lowDeliveryCost = 100
        )
        val expected = 100

        val deliveryCost = getDeliveryCostUseCase(
            isDelivery = true,
            deliveryZone = deliveryZone,
            orderProducts = emptyList(),
            percentDiscount = null,
            company = FakeCompany.create(
                forFreeDelivery = 500,
                deliveryCost = 100
            )
        )

        assertEquals(expected, deliveryCost)
    }

    @Test
    fun `return low delivery cost when product cost is equal to necessary for low delivery`() = runTest {
        val productsCost = 1000
        coEvery {
            calculateOrderProductsNewCostUseCase(any(), any())
        } returns productsCost
        val deliveryZone = FakeDeliveryZone.create(
            normalDeliveryCost = 200,
            forLowDeliveryCost = 1000,
            lowDeliveryCost = 100
        )
        val expected = 100

        val deliveryCost = getDeliveryCostUseCase(
            isDelivery = true,
            deliveryZone = deliveryZone,
            orderProducts = emptyList(),
            percentDiscount = null,
            company = FakeCompany.create(
                forFreeDelivery = 500,
                deliveryCost = 100
            )
        )

        assertEquals(expected, deliveryCost)
    }
}
