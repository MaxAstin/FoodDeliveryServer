package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.domain.feature.clientuser.usecase.UpdateNotificationTokenUseCase
import com.bunbeauty.fooddelivery.service.client_user.ClientUserService
import com.bunbeauty.fooddelivery.service.client_user.IClientUserService
import org.koin.dsl.module

val clientUserModule = module(createdAtStart = true) {
    single<IClientUserService> {
        ClientUserService(
            firebaseAuth = get(),
            clientUserRepository = get(),
            jwtService = get(),
            updateNotificationTokenUseCase = get()
        )
    }
    single { ClientUserRepository() }
    factory {
        UpdateNotificationTokenUseCase(
            clientUserRepository = get()
        )
    }
}
