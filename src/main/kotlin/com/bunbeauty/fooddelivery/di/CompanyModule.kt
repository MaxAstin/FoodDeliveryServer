package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.service.company.CompanyService
import com.bunbeauty.fooddelivery.service.company.ICompanyService
import org.koin.dsl.module

val companyModule = module(createdAtStart = true) {
    single<ICompanyService> { CompanyService(get()) }
    single { CompanyRepository() }
}