package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.request.IRequestRepository
import com.bunbeauty.fooddelivery.data.repo.request.RequestRepository
import com.bunbeauty.fooddelivery.service.ip.IRequestService
import com.bunbeauty.fooddelivery.service.ip.RequestService
import com.bunbeauty.fooddelivery.service.version.IVersionService
import com.bunbeauty.fooddelivery.service.version.VersionService
import org.koin.dsl.module

val requestModule = module(createdAtStart = true) {
    single<IRequestService> { RequestService(get()) }
    single<IRequestRepository> { RequestRepository() }
}