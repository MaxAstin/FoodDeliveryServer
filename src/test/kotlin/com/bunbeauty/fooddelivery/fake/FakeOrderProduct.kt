package com.bunbeauty.fooddelivery.fake

import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.MenuProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductAddition

object FakeOrderProduct {

    fun create(
        uuid: String = "",
        menuProductUuid: String = "",
        count: Int = 1,
        newPrice: Int = 0,
        oldPrice: Int? = null,
        additions: List<OrderProductAddition> = emptyList(),
    ): OrderProduct {
        return OrderProduct(
            uuid = uuid,
            count = count,
            name = "",
            newPrice = newPrice,
            oldPrice = oldPrice,
            utils = null,
            nutrition = null,
            description = "",
            comboDescription = "",
            photoLink = "",
            barcode = 0,
            menuProduct = MenuProduct(
                uuid = menuProductUuid,
                name = "",
                newPrice = 0,
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
                companyUuid = "",
            ),
            additions = additions,
        )
    }
}