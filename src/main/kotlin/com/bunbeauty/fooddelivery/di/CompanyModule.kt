package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.feature.company.CompanyService
import com.bunbeauty.fooddelivery.domain.feature.company.usecase.GetWorkInfoUseCase
import org.koin.dsl.module

val companyModule = module(createdAtStart = true) {
    factory {
        GetWorkInfoUseCase(companyRepository = get())
    }
    factory {
        CompanyService(
            companyRepository = get(),
            getWorkInfoUseCase = get()
        )
    }
    single { CompanyRepository() }
}
