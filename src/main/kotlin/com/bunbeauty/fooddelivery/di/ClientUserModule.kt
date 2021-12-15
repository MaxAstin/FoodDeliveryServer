package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.client_user.ClientUserRepository
import com.bunbeauty.fooddelivery.data.repo.client_user.IClientUserRepository
import com.bunbeauty.fooddelivery.service.client_user.ClientUserService
import com.bunbeauty.fooddelivery.service.client_user.IClientUserService
import org.koin.dsl.module

val clientUserModule = module(createdAtStart = true) {
    single<IClientUserService> { ClientUserService(get(), get(), get()) }
    single<IClientUserRepository> { ClientUserRepository() }
}