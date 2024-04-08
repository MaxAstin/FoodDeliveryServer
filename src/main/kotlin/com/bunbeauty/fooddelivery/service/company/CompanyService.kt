package com.bunbeauty.fooddelivery.service.company

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.model.company.*
import com.bunbeauty.fooddelivery.domain.toUuid

class CompanyService(private val companyRepository: CompanyRepository) : ICompanyService {

    override suspend fun createCompany(postCompany: PostCompany): GetCompany {
        if ((postCompany.percentDiscount ?: 0) > 99) {
            error("percentDiscount can't be greater then 99")
        }
        val insertCompany = InsertCompany(
            name = postCompany.name,
            forFreeDelivery = postCompany.forFreeDelivery,
            deliveryCost = postCompany.deliveryCost,
            forceUpdateVersion = postCompany.forceUpdateVersion,
            percentDiscount = postCompany.percentDiscount,
        )
        return companyRepository.insertCompany(insertCompany)
    }

    override suspend fun changeCompanyByUuid(companyUuid: String, patchCompany: PatchCompany): GetCompany? {
        if ((patchCompany.percentDiscount ?: 0) > 99) {
            error("percentDiscount can't be greater then 99")
        }
        val updateCompany = UpdateCompany(
            uuid = companyUuid.toUuid(),
            name = patchCompany.name,
            forFreeDelivery = patchCompany.forFreeDelivery,
            deliveryCost = patchCompany.deliveryCost,
            forceUpdateVersion = patchCompany.forceUpdateVersion,
            percentDiscount = patchCompany.percentDiscount,
        )
        return companyRepository.updateCompany(updateCompany)
    }
}