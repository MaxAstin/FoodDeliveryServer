package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.service.client_user.ClientUserService
import com.bunbeauty.fooddelivery.service.client_user.IClientUserService
import org.koin.dsl.module

val clientUserModule = module(createdAtStart = true) {
    single<IClientUserService> { ClientUserService(get(), get(), get()) }
    single { ClientUserRepository() }
}
