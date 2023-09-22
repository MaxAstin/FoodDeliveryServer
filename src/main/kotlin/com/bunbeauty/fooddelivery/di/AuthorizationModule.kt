package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.AuthorizationRepository
import com.bunbeauty.fooddelivery.service.AuthorizationService
import org.koin.dsl.module

val authorizationModule = module(createdAtStart = true) {
    factory {
        AuthorizationService(
            requestService = get(),
            authorizationRepository = get(),
            companyRepository = get(),
            networkService = get(),
            clientUserRepository = get(),
            jwtService = get(),
        )
    }
    single { AuthorizationRepository() }
}