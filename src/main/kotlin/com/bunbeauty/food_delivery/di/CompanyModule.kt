package com.bunbeauty.food_delivery.di

import com.bunbeauty.food_delivery.data.repo.company.CompanyRepository
import com.bunbeauty.food_delivery.data.repo.company.ICompanyRepository
import com.bunbeauty.food_delivery.service.company.CompanyService
import com.bunbeauty.food_delivery.service.company.ICompanyService
import org.koin.dsl.module

val companyModule = module(createdAtStart = true) {
    single<ICompanyService> { CompanyService(get(), get()) }
    single<ICompanyRepository> { CompanyRepository() }
}