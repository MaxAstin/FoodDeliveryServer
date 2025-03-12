package com.bunbeauty.fooddelivery.domain.feature.company

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUserUuidError
import com.bunbeauty.fooddelivery.domain.feature.company.mapper.mapCompanyWithCafes
import com.bunbeauty.fooddelivery.domain.feature.company.usecase.GetWorkInfoByCompanyUseCase
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkInfo
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.model.company.GetCompany
import com.bunbeauty.fooddelivery.domain.model.company.InsertCompany
import com.bunbeauty.fooddelivery.domain.model.company.PatchCompany
import com.bunbeauty.fooddelivery.domain.model.company.PostCompany
import com.bunbeauty.fooddelivery.domain.model.company.UpdateCompany
import com.bunbeauty.fooddelivery.domain.toUuid

private const val MAX_PERCENT_DISCOUNT = 99

class CompanyService(
    private val companyRepository: CompanyRepository,
    private val getWorkInfoByCompanyUseCase: GetWorkInfoByCompanyUseCase
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
            percentDiscount = postCompany.percentDiscount
        )
        val company = companyRepository.insertCompany(insertCompany)

        return company.mapCompanyWithCafes()
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
            workType = patchCompany.workType?.let { workType ->
                WorkType.valueOf(workType)
            }
        )
        val company = companyRepository.updateCompany(updateCompany)
            .orThrowNotFoundByUserUuidError(uuid = companyUuid)

        return company.mapCompanyWithCafes()
    }

    suspend fun getWorkInfo(companyUuid: String): WorkInfo {
        return getWorkInfoByCompanyUseCase(companyUuid = companyUuid)
    }

    private fun errorMaxPercentDiscount() {
        error("PercentDiscount can't be greater then $MAX_PERCENT_DISCOUNT")
    }
}
