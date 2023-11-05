package com.bunbeauty.fooddelivery.service.street

import com.bunbeauty.fooddelivery.data.repo.street.IStreetRepository
import com.bunbeauty.fooddelivery.domain.model.street.GetStreet
import com.bunbeauty.fooddelivery.domain.model.street.InsertStreet
import com.bunbeauty.fooddelivery.domain.model.street.PostStreet
import com.bunbeauty.fooddelivery.domain.toUuid

class StreetService(private val streetRepository: IStreetRepository) : IStreetService {

    override suspend fun createStreet(postStreet: PostStreet): GetStreet {
        val insertStreet = InsertStreet(
            name = postStreet.name,
            cafeUuid = postStreet.cafeUuid.toUuid(),
            isVisible = postStreet.isVisible
        )

        return streetRepository.insertStreet(insertStreet)
    }

    override suspend fun getStreetListByCompanyUuid(cityUuid: String): List<GetStreet> {
        return streetRepository.getStreetListByCityUuid(cityUuid.toUuid())
    }
}