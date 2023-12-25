package com.bunbeauty.fooddelivery

import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.features.menu.HitRepository
import com.bunbeauty.fooddelivery.data.features.menu.MenuProductRepository
import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.GetMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.service.HitService
import com.bunbeauty.fooddelivery.domain.feature.order.model.GetOrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.client.GetClientOrderV2
import com.bunbeauty.fooddelivery.domain.feature.order.model.v2.client.GetOrderAddressV2
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
            getFakeOrder(
                status = OrderStatus.CANCELED.name,
                orderProductList = listOf(
                    getFakeOrderProduct("1", 20, 400),
                    getFakeOrderProduct("2", 15, 100),
                    getFakeOrderProduct("3", 10, 500),
                )
            ),
            getFakeOrder(
                status = OrderStatus.DELIVERED.name,
                orderProductList = listOf(
                    getFakeOrderProduct("4", 1, 20),
                    getFakeOrderProduct("5", 5, 100),
                    getFakeOrderProduct("6", 14, 200),
                )
            ),
            getFakeOrder(
                status = OrderStatus.DELIVERED.name,
                orderProductList = listOf(
                    getFakeOrderProduct("5", 25, 100),
                    getFakeOrderProduct("7", 5, 1000),
                    getFakeOrderProduct("8", 6, 400),
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

    private fun getFakeOrder(status: String, orderProductList: List<GetOrderProduct>): GetClientOrderV2 {
        return GetClientOrderV2(
            uuid = "",
            code = "",
            status = status,
            time = 0,
            timeZone = "UTC+3",
            isDelivery = true,
            deferredTime = null,
            address = GetOrderAddressV2(
                description = "",
                street = "",
                house = "",
                flat = "",
                entrance = "",
                floor = "",
                comment = "",
            ),
            comment = "",
            deliveryCost = 100,
            oldTotalCost = 250,
            newTotalCost = 245,
            paymentMethod = null,
            percentDiscount = null,
            clientUserUuid = "",
            oderProductList = orderProductList,
        )
    }

    private fun getFakeOrderProduct(
        menuProductUuid: String,
        count: Int,
        price: Int,
    ): GetOrderProduct {
        return GetOrderProduct(
            uuid = "",
            count = count,
            name = "",
            newPrice = price,
            oldPrice = null,
            utils = null,
            nutrition = null,
            description = "",
            comboDescription = "",
            photoLink = "",
            barcode = 0,
            menuProduct = GetMenuProduct(
                uuid = menuProductUuid,
                name = "",
                newPrice = price,
                oldPrice = null,
                utils = null,
                nutrition = null,
                description = "",
                comboDescription = "",
                photoLink = "",
                barcode = 0,
                isRecommended = false,
                isVisible = true,
                categories = listOf(),
                additionGroups = listOf(),
            ),
            orderUuid = "",
            additions = emptyList(),
        )
    }

}