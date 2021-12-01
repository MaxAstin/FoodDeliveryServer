package com.bunbeauty.food_delivery.service.street

import com.bunbeauty.food_delivery.data.ext.toUuid
import com.bunbeauty.food_delivery.data.model.street.GetStreet
import com.bunbeauty.food_delivery.data.model.street.InsertStreet
import com.bunbeauty.food_delivery.data.model.street.PostStreet
import com.bunbeauty.food_delivery.data.repo.street.IStreetRepository

class StreetService(private val streetRepository: IStreetRepository) : IStreetService {

    override suspend fun createStreet(postStreet: PostStreet): GetStreet {
        val insertStreet = InsertStreet(
            name = postStreet.name,
            cafeUuid = postStreet.cafeUuid.toUuid(),
            isVisible = postStreet.isVisible
        )

        return streetRepository.insertStreet(insertStreet)
    }

    override suspend fun getStreetListByCompanyUuid(companyUuid: String): List<GetStreet> {
        return streetRepository.getStreetListByCompanyUuid(companyUuid.toUuid())
    }
}