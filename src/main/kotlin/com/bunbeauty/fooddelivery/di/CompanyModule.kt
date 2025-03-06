package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.feature.company.CompanyService
import com.bunbeauty.fooddelivery.domain.feature.company.usecase.GetWorkInfoByCompanyUseCase
import org.koin.dsl.module

val companyModule = module(createdAtStart = true) {
    factory {
        GetWorkInfoByCompanyUseCase(companyRepository = get())
    }
    factory {
        CompanyService(
            companyRepository = get(),
            getWorkInfoByCompanyUseCase = get()
        )
    }
    single { CompanyRepository() }
}
