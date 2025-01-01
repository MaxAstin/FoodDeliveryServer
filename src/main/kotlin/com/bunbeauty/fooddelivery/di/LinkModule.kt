package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.LinkRepository
import com.bunbeauty.fooddelivery.service.LinkService
import org.koin.dsl.module

val linkModule = module(createdAtStart = true) {
    factory { LinkService(linkRepository = get()) }
    single { LinkRepository() }
}
