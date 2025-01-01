package com.bunbeauty.fooddelivery.domain.feature.order.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.MenuProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.InsertOrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.PostOrderProduct

val mapPostOrderProduct: PostOrderProduct.() -> InsertOrderProduct = {
    InsertOrderProduct(
        menuProductUuid = menuProductUuid,
        count = count
    )
}

val mapPostOrderProductToOrderProduct: PostOrderProduct.(MenuProduct) -> OrderProduct = { menuProduct ->
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
        additions = emptyList(),
    )
}