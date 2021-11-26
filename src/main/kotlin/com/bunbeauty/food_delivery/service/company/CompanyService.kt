package com.bunbeauty.food_delivery.service.company

import com.bunbeauty.food_delivery.data.model.company.InsertCompany
import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.model.company.PostCompany
import com.bunbeauty.food_delivery.data.repo.company.ICompanyRepository

class CompanyService(private val companyRepository: ICompanyRepository) :
    ICompanyService {

    override suspend fun createCompany(postCompany: PostCompany): GetCompany {
        val insertCompany = InsertCompany(
            name = postCompany.name
        )
        return companyRepository.insertCompany(insertCompany)
    }
}