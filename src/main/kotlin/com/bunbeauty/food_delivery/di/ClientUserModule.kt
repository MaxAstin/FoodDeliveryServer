package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.data.repo.client_user.ClientUserRepository
import com.bunbeauty.food_delivery.data.repo.client_user.IClientUserRepository
import com.bunbeauty.food_delivery.data.repo.user.IUserRepository
import com.bunbeauty.food_delivery.data.repo.user.UserRepository
import com.bunbeauty.food_delivery.service.client_user.ClientUserService
import com.bunbeauty.food_delivery.service.client_user.IClientUserService
import com.bunbeauty.food_delivery.service.user.IUserService
import com.bunbeauty.food_delivery.service.user.UserService
import org.koin.dsl.module

val clientUserModule = module(createdAtStart = true) {
    single<IClientUserService> { ClientUserService(get(), get(), get()) }
    single<IClientUserRepository> { ClientUserRepository() }
}