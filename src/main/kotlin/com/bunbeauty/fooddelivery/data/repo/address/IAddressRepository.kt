package com.bunbeauty.fooddelivery.data.repo.address

import com.bunbeauty.fooddelivery.data.model.address.GetAddress
import com.bunbeauty.fooddelivery.data.model.address.InsertAddress
import java.util.UUID

interface IAddressRepository {

    suspend fun insertAddress(insertAddress: InsertAddress): GetAddress
    suspend fun getAddressListByUserUuidAndCityUuid(userUuid: UUID, cityUuid: UUID): List<GetAddress>
}