package com.bunbeauty.food_delivery.data.repo.address

import com.bunbeauty.food_delivery.data.model.address.GetAddress
import com.bunbeauty.food_delivery.data.model.address.InsertAddress
import java.util.*

interface IAddressRepository {

    suspend fun insertAddress(insertAddress: InsertAddress): GetAddress
    suspend fun getAddressListByUserUuid(uuid: UUID): List<GetAddress>
}