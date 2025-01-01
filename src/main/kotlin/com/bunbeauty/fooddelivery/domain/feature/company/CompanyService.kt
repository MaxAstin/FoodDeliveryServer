package com.bunbeauty.fooddelivery.domain.feature.company

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUserUuidError
import com.bunbeauty.fooddelivery.domain.feature.company.mapper.mapCompany
import com.bunbeauty.fooddelivery.domain.feature.company.usecase.GetWorkInfoUseCase
import com.bunbeauty.fooddelivery.domain.model.company.*
import com.bunbeauty.fooddelivery.domain.model.company.work_info.WorkInfo
import com.bunbeauty.fooddelivery.domain.toUuid

private const val MAX_PERCENT_DISCOUNT = 99

class CompanyService(
    private val companyRepository: CompanyRepository,
    private val getWorkInfoUseCase: GetWorkInfoUseCase
) {

    suspend fun createCompany(postCompany: PostCompany): GetCompany {
        if ((postCompany.percentDiscount ?: 0) > MAX_PERCENT_DISCOUNT) {
            errorMaxPercentDiscount()
        }
        val insertCompany = InsertCompany(
            name = postCompany.name,
            forFreeDelivery = postCompany.forFreeDelivery,
            deliveryCost = postCompany.deliveryCost,
            forceUpdateVersion = postCompany.forceUpdateVersion,
            percentDiscount = postCompany.percentDiscount,
        )
        val company = companyRepository.insertCompany(insertCompany)

        return company.mapCompany()
    }

    suspend fun changeCompanyByUuid(companyUuid: String, patchCompany: PatchCompany): GetCompany {
        if ((patchCompany.percentDiscount ?: 0) > MAX_PERCENT_DISCOUNT) {
            errorMaxPercentDiscount()
        }
        val updateCompany = UpdateCompany(
            uuid = companyUuid.toUuid(),
            name = patchCompany.name,
            forFreeDelivery = patchCompany.forFreeDelivery,
            deliveryCost = patchCompany.deliveryCost,
            forceUpdateVersion = patchCompany.forceUpdateVersion,
            percentDiscount = patchCompany.percentDiscount,
            isOpen = patchCompany.isOpen,
        )
        val company = companyRepository.updateCompany(updateCompany)
            .orThrowNotFoundByUserUuidError(uuid = companyUuid)

        return company.mapCompany()
    }

    suspend fun getWorkInfo(companyUuid: String): WorkInfo {
        return getWorkInfoUseCase.invoke(companyUuid = companyUuid)
    }

    private fun errorMaxPercentDiscount() {
        error("PercentDiscount can't be greater then $MAX_PERCENT_DISCOUNT")
    }
}