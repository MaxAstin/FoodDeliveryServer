package com.bunbeauty.fooddelivery

import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.features.menu.HitRepository
import com.bunbeauty.fooddelivery.data.features.menu.MenuProductRepository
import com.bunbeauty.fooddelivery.data.features.order.OrderRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithCity
import com.bunbeauty.fooddelivery.domain.feature.city.City
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserLight
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.company.Delivery
import com.bunbeauty.fooddelivery.domain.feature.company.Payment
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.MenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.service.HitService
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct
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

    private fun getFakeOrder(status: String, orderProductList: List<OrderProduct>): Order {
        return Order(
            uuid = "",
            time = 0,
            isDelivery = true,
            code = "",
            addressDescription = "",
            addressStreet = "",
            addressHouse = "",
            addressFlat = "",
            addressEntrance = "",
            addressFloor = "",
            addressComment = "",
            comment = "",
            deferredTime = null,
            status = status,
            deliveryCost = null,
            paymentMethod = "",
            percentDiscount = null,
            cafeWithCity = CafeWithCity(
                cafe = Cafe(
                    uuid = "",
                    fromTime = 0,
                    toTime = 0,
                    offset = 3,
                    phone = "",
                    latitude = 0.0,
                    longitude = 0.0,
                    address = "",
                    cityUuid = "",
                    isVisible = true,
                    zones = emptyList(),
                ),
                city = City(
                    uuid = "",
                    name = "",
                    timeZone = "",
                    isVisible = true,
                ),
            ),
            company = Company(
                uuid = "",
                name = "",
                offset = 3,
                delivery = Delivery(
                    forFree = 0,
                    cost = 0,
                ),
                forceUpdateVersion = 0,
                payment = Payment(
                    phoneNumber = null,
                    cardNumber = null,
                ),
                percentDiscount = null,
                maxVisibleRecommendationCount = 0,
                citiesWithCafes = emptyList(),
            ),
            clientUser = ClientUserLight(
                uuid = "",
                phoneNumber = "",
                email = "",
            ),
            oderProducts = orderProductList,
        )
    }

    private fun getFakeOrderProduct(
        menuProductUuid: String,
        count: Int,
        price: Int,
    ): OrderProduct {
        return OrderProduct(
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
            menuProduct = MenuProduct(
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
            additions = emptyList(),
        )
    }

}