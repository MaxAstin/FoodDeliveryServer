package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductAddition

object FakeOrderProductAddition {

    fun create(price: Int? = null): OrderProductAddition {
        return OrderProductAddition(
            uuid = "",
            name = "",
            price = price,
            priority = 1,
        )
    }

}