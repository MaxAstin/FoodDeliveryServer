package com.bunbeauty.fooddelivery.service.address

import com.bunbeauty.fooddelivery.data.model.address.GetAddress
import com.bunbeauty.fooddelivery.data.model.address.PostAddress

interface IAddressService {

    suspend fun createAddress(userUuid: String, postAddress: PostAddress): GetAddress
    suspend fun getAddressListByUserUuidAndCityUuid(userUuid: String, cityUuid: String): List<GetAddress>
}