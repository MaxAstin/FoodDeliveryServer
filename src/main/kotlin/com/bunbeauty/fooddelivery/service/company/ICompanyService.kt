package com.bunbeauty.fooddelivery.service.company

import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.company.PatchCompany
import com.bunbeauty.fooddelivery.data.model.company.PostCompany

interface ICompanyService {

    suspend fun createCompany(postCompany: PostCompany): GetCompany
    suspend fun changeCompanyByUuid(companyUuid: String, patchCompany: PatchCompany): GetCompany?
}