package com.bunbeauty.fooddelivery.data.repo.street

import com.bunbeauty.fooddelivery.data.model.street.GetStreet
import com.bunbeauty.fooddelivery.data.model.street.InsertStreet
import java.util.*

interface IStreetRepository {

    suspend fun insertStreet(insertStreet: InsertStreet): GetStreet
    suspend fun getStreetListByCityUuid(cityUuid: UUID): List<GetStreet>
    suspend fun getStreetByAddressUuid(addressUuid: UUID): GetStreet?
    suspend fun getStreetByUuid(streetUuid: UUID): GetStreet?
}