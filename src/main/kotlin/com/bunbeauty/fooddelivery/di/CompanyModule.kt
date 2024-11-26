package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.feature.company.CompanyService
import org.koin.dsl.module

val companyModule = module(createdAtStart = true) {
    factory { CompanyService(companyRepository = get()) }
    single { CompanyRepository() }
}