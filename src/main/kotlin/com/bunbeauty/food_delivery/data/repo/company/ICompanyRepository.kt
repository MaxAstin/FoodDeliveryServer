package com.bunbeauty.food_delivery.data.repo.company

import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.model.company.InsertCompany

interface ICompanyRepository {

    suspend fun insertCompany(insertCompany: InsertCompany): GetCompany
}