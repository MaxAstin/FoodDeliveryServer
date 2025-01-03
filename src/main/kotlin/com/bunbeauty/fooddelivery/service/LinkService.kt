package com.bunbeauty.fooddelivery.service

import com.bunbeauty.fooddelivery.data.repo.LinkRepository
import com.bunbeauty.fooddelivery.domain.model.company.link.GetLink
import com.bunbeauty.fooddelivery.domain.toUuid

class LinkService(
    private val linkRepository: LinkRepository
) {

    suspend fun getLinksByCompanyUuid(companyUuid: String): List<GetLink> {
        return linkRepository.getLinkListByCompanyUuid(companyUuid.toUuid())
    }
}
