package com.bunbeauty.fooddelivery.service.company

import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.company.InsertCompany
import com.bunbeauty.fooddelivery.data.model.company.PostCompany
import com.bunbeauty.fooddelivery.data.repo.company.ICompanyRepository

class CompanyService(private val companyRepository: ICompanyRepository) :
    ICompanyService {

    override suspend fun createCompany(postCompany: PostCompany): GetCompany {
        val insertCompany = InsertCompany(
            name = postCompany.name,
            forFreeDelivery = postCompany.forFreeDelivery,
            deliveryCost = postCompany.deliveryCost
        )
        return companyRepository.insertCompany(insertCompany)
    }
}