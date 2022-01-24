package com.bunbeauty.fooddelivery

import at.favre.lib.crypto.bcrypt.BCrypt
import com.bunbeauty.fooddelivery.data.enums.OrderStatus
import com.bunbeauty.fooddelivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.fooddelivery.data.model.order.GetCafeOrder
import com.bunbeauty.fooddelivery.data.model.order.GetOrderProduct
import com.bunbeauty.fooddelivery.service.menu_product.MenuProductService
import com.toxicbakery.bcrypt.Bcrypt
import com.toxicbakery.bcrypt.Bcrypt.verify
import io.mockk.mockk
import org.joda.time.DateTime
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue

class ApplicationTest {

    private val menuProductService = MenuProductService(
        menuProductRepository = mockk(),
        orderRepository = mockk(),
        userRepository = mockk(),
        categoryRepository = mockk(),
    )

    @Test
    fun testHashing() {
        val value = "920855"
        val hash = String(Bcrypt.hash(value, BCrypt.MIN_COST))
        println(hash)
        val isVerified = verify(value, "hash".toByteArray())
        assertTrue(isVerified)
    }

    @Test
    fun testHitMenuProductUuidList() {
        val hitCount = 3
        val orderList = listOf(
            getFakeOrder(OrderStatus.DELIVERED.name, getFakeTime(1), listOf(
                getFakeOrderProduct("1", 12, 800),
                getFakeOrderProduct("2", 11, 800),
                getFakeOrderProduct("3", 10, 800),
                getFakeOrderProduct("4", 1, 1000),
                getFakeOrderProduct("5", 10, 500),
            )),
            getFakeOrder(OrderStatus.DELIVERED.name, getFakeTime(25), listOf(
                getFakeOrderProduct("1", 12, 800),
                getFakeOrderProduct("2", 11, 800),
                getFakeOrderProduct("3", 10, 800),
                getFakeOrderProduct("4", 1, 1000),
                getFakeOrderProduct("5", 10, 500),
            )),
            getFakeOrder(OrderStatus.CANCELED.name, getFakeTime(3), listOf(
                getFakeOrderProduct("6", 500, 10000),
            )),
        )
        val expectedMenuProductUuidList = listOf("1", "2", "3")

        val hitMenuProductUuidList = menuProductService.getHitMenuProductUuidList(orderList, hitCount)

        assertContentEquals(expectedMenuProductUuidList, hitMenuProductUuidList)
    }

    private fun getFakeTime(daysAgo: Int): Long {
        return DateTime.now().minusDays(daysAgo).millis
    }

    private fun getFakeOrder(status: String, time: Long, orderProductList: List<GetOrderProduct>): GetCafeOrder {
        return GetCafeOrder(
            uuid = "",
            code = "",
            status = status,
            time = time,
            isDelivery = true,
            deferredTime = null,
            addressDescription = "",
            comment = "",
            deliveryCost = null,
            clientUser = mockk(),
            cafeUuid = "",
            oderProductList = orderProductList
        )
    }

    private fun getFakeOrderProduct(menuProductUuid: String, count: Int, price: Int): GetOrderProduct {
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
                categories = mutableListOf(),
                isVisible = true,
            ),
            orderUuid = "",
        )
    }
}