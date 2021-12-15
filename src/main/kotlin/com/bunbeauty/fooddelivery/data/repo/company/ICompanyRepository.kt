package com.bunbeauty.fooddelivery.data.repo.company

import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.company.InsertCompany
import java.util.*

interface ICompanyRepository {

    suspend fun insertCompany(insertCompany: InsertCompany): GetCompany
    suspend fun getCompanyByUuid(uuid: UUID): GetCompany?
}