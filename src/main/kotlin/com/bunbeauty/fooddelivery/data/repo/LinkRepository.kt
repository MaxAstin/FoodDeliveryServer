package com.bunbeauty.fooddelivery.data.repo

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.LinkEntity
import com.bunbeauty.fooddelivery.data.model.company.link.GetLink
import com.bunbeauty.fooddelivery.data.table.LinkTable
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