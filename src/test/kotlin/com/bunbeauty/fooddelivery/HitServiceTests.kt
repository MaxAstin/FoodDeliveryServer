package com.bunbeauty.fooddelivery

import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.features.menu.HitRepository
import com.bunbeauty.fooddelivery.data.features.menu.MenuProductRepository
import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.feature.menu.service.HitService
import com.bunbeauty.fooddelivery.fake.FakeOrder
import com.bunbeauty.fooddelivery.fake.FakeOrderProduct
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals

class HitServiceTests {

    @MockK
    private lateinit var companyRepository: CompanyRepository

    @MockK
    private lateinit var menuProductRepository: MenuProductRepository

    @MockK
    private lateinit var orderRepository: OrderRepository

    @MockK
    private lateinit var hitRepository: HitRepository

    @InjectMockKs
    private lateinit var hitService: HitService

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testHitMenuProductUuidList() {
        // 1, 2, 3 - not delivered
        // 4 - small amount
        // 7 - invisible product
        // 5, 6, 8
        val orderList = listOf(
            FakeOrder.create(
                status = OrderStatus.CANCELED.name,
                orderProductList = listOf(
                    FakeOrderProduct.create(
                        menuProductUuid = "1",
                        count = 20,
                        newPrice = 400,
                    ),
                    FakeOrderProduct.create(
                        menuProductUuid = "2",
                        count = 15,
                        newPrice = 100,
                    ),
                    FakeOrderProduct.create(
                        menuProductUuid = "3",
                        count = 10,
                        newPrice = 500,
                    ),
                )
            ),
            FakeOrder.create(
                orderProductList = listOf(
                    FakeOrderProduct.create(
                        menuProductUuid = "4",
                        count = 1,
                        newPrice = 20,
                    ),
                    FakeOrderProduct.create(
                        menuProductUuid = "5",
                        count = 5,
                        newPrice = 100,
                    ),
                    FakeOrderProduct.create(
                        menuProductUuid = "6",
                        count = 14,
                        newPrice = 200,
                    ),
                )
            ),
            FakeOrder.create(
                orderProductList = listOf(
                    FakeOrderProduct.create(
                        menuProductUuid = "5",
                        count = 25,
                        newPrice = 100,
                    ),
                    FakeOrderProduct.create(
                        menuProductUuid = "7",
                        count = 5,
                        newPrice = 1000,
                    ),
                    FakeOrderProduct.create(
                        menuProductUuid = "8",
                        count = 6,
                        newPrice = 400,
                    ),
                )
            )
        )
        val invisibleMenuProductListUuid = listOf("7")
        val count = 3

        val expectedMenuProductUuidList = listOf("5", "6", "8")

        val hitMenuProductUuidList = hitService.getHitMenuProductUuidList(
            orderList = orderList,
            invisibleMenuProductUuidList = invisibleMenuProductListUuid,
            count = count
        )

        assertContentEquals(expectedMenuProductUuidList, hitMenuProductUuidList)
    }

}