package com.bunbeauty.food_delivery.service.address

import com.bunbeauty.food_delivery.data.ext.toUuid
import com.bunbeauty.food_delivery.data.model.address.GetAddress
import com.bunbeauty.food_delivery.data.model.address.InsertAddress
import com.bunbeauty.food_delivery.data.model.address.PostAddress
import com.bunbeauty.food_delivery.data.repo.address.IAddressRepository

class AddressService(private val addressRepository: IAddressRepository) : IAddressService {

    override suspend fun createAddress(userUuid: String, postAddress: PostAddress): GetAddress {
        val insertAddress = InsertAddress(
            house = postAddress.house,
            flat = postAddress.flat,
            entrance = postAddress.entrance,
            floor = postAddress.floor,
            comment = postAddress.comment,
            streetUuid = postAddress.streetUuid.toUuid(),
            userUuid = userUuid.toUuid(),
            isVisible = postAddress.isVisible,
        )

        return addressRepository.insertAddress(insertAddress)
    }

    override suspend fun getAddressListByUserUuid(userUuid: String): List<GetAddress> =
        addressRepository.getAddressListByUserUuid(userUuid.toUuid())
}