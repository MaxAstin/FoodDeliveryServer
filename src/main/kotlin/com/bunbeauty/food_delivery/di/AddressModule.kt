package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.data.repo.address.AddressRepository
import com.bunbeauty.food_delivery.data.repo.address.IAddressRepository
import com.bunbeauty.food_delivery.service.address.AddressService
import com.bunbeauty.food_delivery.service.address.IAddressService
import org.koin.dsl.module

val addressModule = module(createdAtStart = true) {
    single<IAddressService> { AddressService(get()) }
    single<IAddressRepository> { AddressRepository() }
}