package com.bunbeauty.fooddelivery.service.address

import com.bunbeauty.fooddelivery.domain.model.address.GetAddress
import com.bunbeauty.fooddelivery.domain.model.address.PostAddress

interface IAddressService {

    suspend fun createAddress(userUuid: String, postAddress: PostAddress): GetAddress
    suspend fun getAddressListByUserUuidAndCityUuid(userUuid: String, cityUuid: String): List<GetAddress>
}