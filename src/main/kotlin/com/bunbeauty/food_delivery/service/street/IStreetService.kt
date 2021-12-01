package com.bunbeauty.food_delivery.service.street

import com.bunbeauty.food_delivery.data.model.street.GetStreet
import com.bunbeauty.food_delivery.data.model.street.PostStreet

interface IStreetService {

    suspend fun createStreet(postStreet: PostStreet): GetStreet
    suspend fun getStreetListByCompanyUuid(companyUuid: String): List<GetStreet>
}