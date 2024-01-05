package com.bunbeauty.fooddelivery.domain.feature.order.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.mapMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.GetOrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.GetOrderProductAddition
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductTotal

val mapOrderProduct: OrderProduct.(String, OrderProductTotal) -> GetOrderProduct =
    { orderUuid, orderProductTotal ->
        GetOrderProduct(
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
            menuProduct = menuProduct.mapMenuProduct(),
            orderUuid = orderUuid,
            additionsPrice = orderProductTotal.additionsPrice,
            newTotalCost = orderProductTotal.newTotalCost,
            oldTotalCost = orderProductTotal.oldTotalCost,
            additions = additions.map { addition ->
                GetOrderProductAddition(
                    uuid = addition.uuid,
                    name = addition.name,
                )
            },
        )
    }