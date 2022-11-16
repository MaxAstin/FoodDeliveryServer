package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.address.AddressRepository
import com.bunbeauty.fooddelivery.data.repo.address.IAddressRepository
import com.bunbeauty.fooddelivery.service.address.AddressService
import com.bunbeauty.fooddelivery.service.address.IAddressService
import org.koin.dsl.module

val addressModule = module(createdAtStart = true) {
    single<IAddressService> {
        AddressService(
            addressRepository = get(),
            streetRepository = get(),
            clientUserRepository = get(),
            cityRepository = get(),
        )
    }
    single<IAddressRepository> { AddressRepository() }
}