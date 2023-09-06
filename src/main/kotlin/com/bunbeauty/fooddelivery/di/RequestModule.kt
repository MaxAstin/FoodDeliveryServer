package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.request.IRequestRepository
import com.bunbeauty.fooddelivery.data.repo.request.RequestRepository
import com.bunbeauty.fooddelivery.service.ip.RequestService
import org.koin.dsl.module

val requestModule = module(createdAtStart = true) {
    factory { RequestService(get()) }
    single<IRequestRepository> { RequestRepository() }
}