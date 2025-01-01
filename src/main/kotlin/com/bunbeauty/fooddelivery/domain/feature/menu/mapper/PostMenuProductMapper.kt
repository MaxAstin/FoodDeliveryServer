package com.bunbeauty.fooddelivery.domain.feature.menu.mapper

import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.InsertMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.PostMenuProduct
import com.bunbeauty.fooddelivery.domain.mapUuid
import java.util.*

val mapPostMenuProduct: PostMenuProduct.(UUID) -> InsertMenuProduct = { companyUuid ->
    InsertMenuProduct(
        name = name,
        newPrice = newPrice,
        oldPrice = oldPrice,
        utils = utils,
        nutrition = nutrition,
        description = description,
        comboDescription = comboDescription,
        photoLink = photoLink,
        barcode = barcode,
        companyUuid = companyUuid,
        categoryUuids = categoryUuids.map(mapUuid),
        isRecommended = isRecommended,
        isVisible = isVisible
    )
}
