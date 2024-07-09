package com.bunbeauty.fooddelivery.data.features.order.mapper

import com.bunbeauty.fooddelivery.data.entity.order.OrderProductAdditionEntity
import com.bunbeauty.fooddelivery.data.entity.order.OrderProductEntity
import com.bunbeauty.fooddelivery.data.features.menu.mapper.mapMenuProductEntity
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductAddition

val mapOrderProductEntity: OrderProductEntity.() -> OrderProduct = {
    OrderProduct(
        uuid = uuid,
        count = count,
        name = name,
        newPrice = newPrice,
        oldPrice = oldPrice,
        utils = utils,
        nutrition = nutrition,
        description = description,
        comboDescription = comboDescription,
        photoLink = photoLink,
        barcode = barcode,
        menuProduct = menuProduct.mapMenuProductEntity(),
        additions = additions.map(mapOrderProductAdditionEntity),
    )
}

val mapOrderProductAdditionEntity: OrderProductAdditionEntity.() -> OrderProductAddition = {
    OrderProductAddition(
        uuid = uuid,
        name = name,
        price = price,
        priority = priority,
    )
}