package com.bunbeauty.food_delivery.service.company

import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.model.company.PostCompany

interface ICompanyService {

    suspend fun createCompany(creatorUuid: String, postCompany: PostCompany): GetCompany?
    suspend fun createCompany(postCompany: PostCompany): GetCompany
}