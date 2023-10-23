package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.RecommendationRepository
import com.bunbeauty.fooddelivery.service.RecommendationService
import org.koin.dsl.module

val recommendationModule = module(createdAtStart = true) {
    factory {
        RecommendationService(
            companyRepository = get(),
            recommendationRepository = get(),
            menuProductRepository = get(),
        )
    }
    single { RecommendationRepository() }
}