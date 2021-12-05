package com.bunbeauty.fooddelivery.service.company

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.company.*
import com.bunbeauty.fooddelivery.data.repo.company.ICompanyRepository

class CompanyService(private val companyRepository: ICompanyRepository) :
    ICompanyService {

    override suspend fun createCompany(postCompany: PostCompany): GetCompany {
        val insertCompany = InsertCompany(
            name = postCompany.name,
            forFreeDelivery = postCompany.forFreeDelivery,
            deliveryCost = postCompany.deliveryCost,
            forceUpdateVersion = postCompany.forceUpdateVersion,
        )
        return companyRepository.insertCompany(insertCompany)
    }

    override suspend fun changeCompanyByUuid(companyUuid: String, patchCompany: PatchCompany): GetCompany? {
        val updateCompany = UpdateCompany(
            uuid = companyUuid.toUuid(),
            name = patchCompany.name,
            forFreeDelivery = patchCompany.forFreeDelivery,
            deliveryCost = patchCompany.deliveryCost,
            forceUpdateVersion = patchCompany.forceUpdateVersion,
        )
        return companyRepository.updateCompany(updateCompany)
    }
}