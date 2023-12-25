package com.bunbeauty.fooddelivery.domain.feature.order.mapper

import com.bunbeauty.fooddelivery.domain.feature.order.model.OrderProductLight
import com.bunbeauty.fooddelivery.domain.feature.order.model.v3.InsertOrderProductV3
import com.bunbeauty.fooddelivery.domain.feature.order.model.v3.PostOrderProductV3

val mapPostOrderProductV3: PostOrderProductV3.() -> InsertOrderProductV3 = {
    InsertOrderProductV3(
        menuProductUuid = menuProductUuid,
        count = count,
        additionUuids = additionUuids,
    )
}

val mapPostOrderProductV3ToLight: PostOrderProductV3.() -> OrderProductLight = {
    OrderProductLight(
        menuProductUuid = menuProductUuid,
        count = count
    )
}