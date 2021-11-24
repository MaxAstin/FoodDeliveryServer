package com.bunbeauty.food_delivery.data.repo.company

import com.bunbeauty.food_delivery.data.entity.conpany.CompanyEntity
import com.bunbeauty.food_delivery.data.entity.conpany.InsertCompany

interface ICompanyRepository {

    suspend fun insertCompany(insertCompany: InsertCompany): CompanyEntity
}