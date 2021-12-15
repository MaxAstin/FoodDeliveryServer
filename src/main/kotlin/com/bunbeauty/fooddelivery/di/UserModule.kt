package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.user.IUserRepository
import com.bunbeauty.fooddelivery.data.repo.user.UserRepository
import com.bunbeauty.fooddelivery.service.user.IUserService
import com.bunbeauty.fooddelivery.service.user.UserService
import org.koin.dsl.module

val userModule = module(createdAtStart = true) {
    single<IUserService> { UserService(get(), get()) }
    single<IUserRepository> { UserRepository() }
}