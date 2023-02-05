package com.bunbeauty.fooddelivery.data.repo.company

import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.company.InsertCompany
import com.bunbeauty.fooddelivery.data.model.company.UpdateCompany
import java.util.*

interface ICompanyRepository {

    suspend fun insertCompany(insertCompany: InsertCompany): GetCompany
    suspend fun updateCompany(updateCompany: UpdateCompany): GetCompany?
    suspend fun getCompanyByUuid(uuid: UUID): GetCompany?
    suspend fun getCompanyByName(name: String): GetCompany?
    suspend fun getCompanyList(): List<GetCompany>
}