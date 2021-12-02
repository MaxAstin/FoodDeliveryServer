package com.bunbeauty.food_delivery.service.address

import com.bunbeauty.food_delivery.data.model.address.GetAddress
import com.bunbeauty.food_delivery.data.model.address.PostAddress

interface IAddressService {

    suspend fun createAddress(userUuid: String, postAddress: PostAddress): GetAddress
    suspend fun getAddressListByUserUuid(userUuid: String): List<GetAddress>
}