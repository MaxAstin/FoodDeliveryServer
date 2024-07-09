package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.Cafe
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.cafe.CafeWithCity
import com.bunbeauty.fooddelivery.domain.feature.city.City
import com.bunbeauty.fooddelivery.domain.feature.clientuser.model.ClientUserLight
import com.bunbeauty.fooddelivery.domain.feature.company.Company
import com.bunbeauty.fooddelivery.domain.feature.company.Delivery
import com.bunbeauty.fooddelivery.domain.feature.company.Payment
import com.bunbeauty.fooddelivery.domain.feature.order.model.Order
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct

object FakeOrder {

    fun create(
        isDelivery: Boolean = false,
        status: String = "DELIVERED",
        deliveryCost: Int? = null,
        percentDiscount: Int? = null,
        orderProductList: List<OrderProduct> = emptyList(),
    ): Order {
        return Order(
            uuid = "",
            time = 0,
            isDelivery = isDelivery,
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
            deliveryCost = deliveryCost,
            paymentMethod = "",
            percentDiscount = percentDiscount,
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
            orderProducts = orderProductList,
        )
    }

}