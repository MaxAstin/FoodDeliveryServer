package com.bunbeauty.fooddelivery.data.repo.client_user

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.ClientUserEntity
import com.bunbeauty.fooddelivery.data.entity.CompanyEntity
import com.bunbeauty.fooddelivery.data.model.client_user.GetClientUser
import com.bunbeauty.fooddelivery.data.model.client_user.InsertClientUser
import com.bunbeauty.fooddelivery.data.table.ClientUserTable
import java.util.*

class ClientUserRepository : IClientUserRepository {

    override suspend fun getClientUserByPhoneNumber(phoneNumber: String): GetClientUser? = query {
        ClientUserEntity.find {
            ClientUserTable.phoneNumber eq phoneNumber
        }.singleOrNull()?.toClientUser()
    }

    override suspend fun getClientUserByUuid(uuid: UUID): GetClientUser? = query {
        ClientUserEntity.findById(uuid)?.toClientUser()
    }

    override suspend fun insertClientUser(insertClientUser: InsertClientUser): GetClientUser = query {
        ClientUserEntity.new {
            phoneNumber = insertClientUser.phoneNumber
            email = insertClientUser.email
            company = CompanyEntity[insertClientUser.companyUuid]
        }.toClientUser()
    }
}