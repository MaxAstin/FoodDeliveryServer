package com.bunbeauty.fooddelivery.data.repo.address

import com.bunbeauty.fooddelivery.domain.model.address.GetAddress
import com.bunbeauty.fooddelivery.domain.model.address.InsertAddress
import java.util.*

interface IAddressRepository {

    suspend fun insertAddress(insertAddress: InsertAddress): GetAddress
    suspend fun getAddressListByUserUuidAndCityUuid(userUuid: UUID, cityUuid: UUID): List<GetAddress>
}