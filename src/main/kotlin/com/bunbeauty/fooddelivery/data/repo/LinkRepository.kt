package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.LinkEntity
import com.bunbeauty.fooddelivery.data.table.LinkTable
import com.bunbeauty.fooddelivery.domain.model.company.link.GetLink
import java.util.*

class LinkRepository {

    suspend fun getLinkListByCompanyUuid(companyUuid: UUID): List<GetLink> = query {
        LinkEntity.find {
            LinkTable.company eq companyUuid
        }.map { linkEntity ->
            linkEntity.toLink()
        }
    }
}
