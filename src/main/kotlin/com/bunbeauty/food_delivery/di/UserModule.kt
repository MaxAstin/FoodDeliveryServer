package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.data.repo.user.IUserRepository
import com.bunbeauty.food_delivery.data.repo.user.UserRepository
import com.bunbeauty.food_delivery.service.user.IUserService
import com.bunbeauty.food_delivery.service.user.UserService
import org.koin.dsl.module

val userModule = module(createdAtStart = true) {
    single<IUserService> { UserService(get(), get()) }
    single<IUserRepository> { UserRepository() }
}