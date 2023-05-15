package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.ext.toUuid
import com.bunbeauty.fooddelivery.data.model.company.link.GetLink
import com.bunbeauty.fooddelivery.data.model.company.payment_method.GetPaymentMethod
import com.bunbeauty.fooddelivery.data.repo.LinkRepository
import com.bunbeauty.fooddelivery.data.repo.payment_method.IPaymentMethodRepository

class LinkService(
    private val linkRepository: LinkRepository
) {

    suspend fun getLinksByCompanyUuid(companyUuid: String): List<GetLink> {
        return linkRepository.getLinkListByCompanyUuid(companyUuid.toUuid())
    }

}