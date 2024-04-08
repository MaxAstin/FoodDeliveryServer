package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.fake.FakeClientUser
import com.bunbeauty.fooddelivery.fake.FakeCompany
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetDeliveryCostUseCaseTest {

    private val clientUser = FakeClientUser.create(
        company = FakeCompany.create(
            forFreeDelivery = 500,
            deliveryCost = 100,
        )
    )
    private val clientUserUuid = "userUuid"
    private val clientUserRepository: ClientUserRepository = mockk {
        coEvery { getClientUserByUuid(clientUserUuid) } returns clientUser
    }
    private val calculateOrderProductsNewCostUseCase: CalculateOrderProductsNewCostUseCase = mockk()
    private lateinit var getDeliveryCostUseCase: GetDeliveryCostUseCase

    @BeforeTest
    fun setup() {
        getDeliveryCostUseCase = GetDeliveryCostUseCase(
            clientUserRepository = clientUserRepository,
            calculateOrderProductsNewCostUseCase = calculateOrderProductsNewCostUseCase,
        )
    }

    @Test
    fun `return null when not delivery`() = runTest {
        val deliveryCost = getDeliveryCostUseCase(
            isDelivery = false,
            clientUserUuid = clientUserUuid,
            orderProducts = emptyList(),
            percentDiscount = null,
        )

        assertNull(deliveryCost)
    }

    @Test
    fun `return zero when products cost is more than necessary for free delivery`() = runTest {
        val productsCost = 600
        coEvery {
            calculateOrderProductsNewCostUseCase(any(), any())
        } returns productsCost
        val expected = 0

        val deliveryCost = getDeliveryCostUseCase(
            isDelivery = true,
            clientUserUuid = clientUserUuid,
            orderProducts = emptyList(),
            percentDiscount = null,
        )

        assertEquals(expected, deliveryCost)
    }

    @Test
    fun `return not zero when products cost is less than necessary for free delivery`() = runTest {
        val productsCost = 200
        coEvery {
            calculateOrderProductsNewCostUseCase(any(), any())
        } returns productsCost
        val expected = 100

        val deliveryCost = getDeliveryCostUseCase(
            isDelivery = true,
            clientUserUuid = clientUserUuid,
            orderProducts = emptyList(),
            percentDiscount = null,
        )

        assertEquals(expected, deliveryCost)
    }


}