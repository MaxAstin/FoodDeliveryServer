package com.bunbeauty.food_delivery.data.mapper.cafe

import com.bunbeauty.food_delivery.data.entity.CafeEntity
import com.bunbeauty.food_delivery.data.model.cafe.GetCafe

interface ICafeMapper {

    fun entityToModel(cafeEntity: CafeEntity): GetCafe
}