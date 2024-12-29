package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductTotal

object FakeOrderProductTotal {

    fun create(
        additionsPrice: Int? = null,
        newCommonPrice: Int = 0,
        oldCommonPrice: Int? = null,
        newTotalCost: Int = 0,
        oldTotalCost: Int? = null
    ): OrderProductTotal {
        return OrderProductTotal(
            additionsPrice = additionsPrice,
            newCommonPrice = newCommonPrice,
            oldCommonPrice = oldCommonPrice,
            newTotalCost = newTotalCost,
            oldTotalCost = oldTotalCost
        )
    }
}
