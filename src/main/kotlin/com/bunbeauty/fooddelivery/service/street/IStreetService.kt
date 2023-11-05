package com.bunbeauty.fooddelivery.service.street

import com.bunbeauty.fooddelivery.domain.model.street.GetStreet
import com.bunbeauty.fooddelivery.domain.model.street.PostStreet

interface IStreetService {

    suspend fun createStreet(postStreet: PostStreet): GetStreet
    suspend fun getStreetListByCompanyUuid(cityUuid: String): List<GetStreet>
}