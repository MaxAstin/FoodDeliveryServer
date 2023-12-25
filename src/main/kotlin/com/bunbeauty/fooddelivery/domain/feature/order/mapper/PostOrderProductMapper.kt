package com.bunbeauty.fooddelivery.domain.feature.order.mapper

import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductLight
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.InsertOrderProduct
import com.bunbeauty.fooddelivery.domain.feature.order.model.v1.PostOrderProduct

val mapPostOrderProduct: PostOrderProduct.() -> InsertOrderProduct = {
    InsertOrderProduct(
        menuProductUuid = menuProductUuid,
        count = count
    )
}

val mapPostOrderProductToLight: PostOrderProduct.() -> OrderProductLight = {
    OrderProductLight(
        menuProductUuid = menuProductUuid,
        count = count
    )
}