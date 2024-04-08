package com.bunbeauty.fooddelivery.domain.feature.order.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.MenuProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductAddition
import com.bunbeauty.fooddelivery.domain.feature.order.model.v3.InsertOrderProductV3
import com.bunbeauty.fooddelivery.domain.feature.order.model.v3.PostOrderProductV3

val mapPostOrderProductV3: PostOrderProductV3.() -> InsertOrderProductV3 = {
    InsertOrderProductV3(
        menuProductUuid = menuProductUuid,
        count = count,
        additionUuids = additionUuids,
    )
}

val mapPostOrderProductV3ToOrderProduct: PostOrderProductV3.(MenuProduct) -> OrderProduct = { menuProduct ->
    OrderProduct(
        uuid = "",
        count = count,
        name = menuProduct.name,
        newPrice = menuProduct.newPrice,
        oldPrice = menuProduct.oldPrice,
        utils = menuProduct.utils,
        nutrition = menuProduct.nutrition,
        description = menuProduct.description,
        comboDescription = menuProduct.comboDescription,
        photoLink = menuProduct.photoLink,
        barcode = menuProduct.barcode,
        menuProduct = menuProduct,
        additions = additionUuids.mapNotNull { additionUuid ->
            val menuProductAddition = menuProduct.additionGroups.map { additionGroup ->
                additionGroup.additions
            }.flatten()
                .find { menuProductAddition ->
                    menuProductAddition.uuid == additionUuid
                }

            menuProductAddition?.let {
                OrderProductAddition(
                    uuid = menuProductAddition.uuid,
                    name = menuProductAddition.name,
                    price = menuProductAddition.price,
                    priority = menuProductAddition.priority,
                )
            }
        },
    )
}