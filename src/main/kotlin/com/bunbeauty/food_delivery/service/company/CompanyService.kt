package com.bunbeauty.food_delivery.service.company

import com.bunbeauty.food_delivery.data.entity.conpany.InsertCompany
import com.bunbeauty.food_delivery.data.enums.UserRole
import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.model.company.PostCompany
import com.bunbeauty.food_delivery.data.repo.company.ICompanyRepository
import com.bunbeauty.food_delivery.data.repo.user.IUserRepository

class CompanyService(private val userRepository: IUserRepository, private val companyRepository: ICompanyRepository) :
    ICompanyService {

    override suspend fun createCompany(creatorUuid: String, postCompany: PostCompany): GetCompany? {
        val creator = userRepository.getUserByUuid(creatorUuid) ?: return null
        if (creator.role != UserRole.ADMIN) {
            return null
        }

        return createCompany(postCompany)
    }

    override suspend fun createCompany(postCompany: PostCompany): GetCompany {
        val insertCompany = InsertCompany(
            name = postCompany.name
        )
        return companyRepository.insertCompany(insertCompany).toModel()
    }
}