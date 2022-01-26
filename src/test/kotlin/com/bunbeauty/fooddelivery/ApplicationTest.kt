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
        val hitCount = 5
        val orderList = listOf(
            getFakeOrder(OrderStatus.DELIVERED.name, getFakeTime(1), listOf(
                getFakeOrderProduct("пицца салями", 1, 400),
                getFakeOrderProduct("бургер с курицей", 6, 149),
                getFakeOrderProduct("пит-бургер с курицей", 2, 150),
                getFakeOrderProduct("картофель фри", 11, 89),
                getFakeOrderProduct("бургер микс", 1, 220),
                getFakeOrderProduct("картофель по дер", 14, 89),
                getFakeOrderProduct("соус кислый", 4, 20),
                getFakeOrderProduct("пит шаурма", 6, 160),
                getFakeOrderProduct("пит-бургер мини с кур", 1, 99),
                getFakeOrderProduct("картофельные шарики", 5, 100),
                getFakeOrderProduct("шаурма со свин", 4, 220),
                getFakeOrderProduct("соус чесночный", 9, 20),
                getFakeOrderProduct("пицца пеперони", 3, 400),
                getFakeOrderProduct("люля свин", 8, 180),
                getFakeOrderProduct("куриные ножки", 1, 290),
                getFakeOrderProduct("пицца мясная", 3, 500),
                getFakeOrderProduct("пицца прошуто", 1, 390),
                getFakeOrderProduct("бургер гов", 1, 240),
                getFakeOrderProduct("хачапури", 5, 170),
                getFakeOrderProduct("шаурма мини с кур", 10, 120),
                getFakeOrderProduct("люля телят", 2, 190),
                getFakeOrderProduct("шаурма с  кур", 12, 170),
                getFakeOrderProduct("шаурма микс", 3, 200),
                getFakeOrderProduct("бургер мини с кур", 4, 99),
                getFakeOrderProduct("пит-бургер мини с гов", 4, 130),
                getFakeOrderProduct("бургер мини микс", 3, 130),
                getFakeOrderProduct("пит-бургер со свин", 2, 210),
                getFakeOrderProduct("пит-бургер с гов", 4, 230),
                getFakeOrderProduct("компот", 1, 69),
                getFakeOrderProduct("морс", 1, 69),
                getFakeOrderProduct("пицца кварто", 1, 450),
                getFakeOrderProduct("шашлык свин", 1, 390),
                getFakeOrderProduct("куриные крылышки", 1, 240),
                getFakeOrderProduct("соус острый", 1, 20),
            ))
        )
        val expectedMenuProductUuidList = listOf("1", "2", "3")

        val hitMenuProductUuidList = menuProductService.getHitMenuProductUuidList(orderList, hitCount)

        println(hitMenuProductUuidList.joinToString())
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