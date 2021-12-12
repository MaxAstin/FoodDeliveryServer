package com.bunbeauty.food_delivery.data.repo.street

import com.bunbeauty.food_delivery.data.model.cafe.GetCafe
import com.bunbeauty.food_delivery.data.model.street.GetStreet
import com.bunbeauty.food_delivery.data.model.street.InsertStreet
import java.util.*

interface IStreetRepository {

    suspend fun insertStreet(insertStreet: InsertStreet): GetStreet
    suspend fun getStreetListByCompanyUuid(companyUuid: UUID): List<GetStreet>
    suspend fun getStreetByAddressUuid(addressUuid: UUID): GetCafe?
}