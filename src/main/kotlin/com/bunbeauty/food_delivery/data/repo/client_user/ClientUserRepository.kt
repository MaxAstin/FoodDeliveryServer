package com.bunbeauty.food_delivery.data.repo.client_user

import com.bunbeauty.food_delivery.data.DatabaseFactory.query
import com.bunbeauty.food_delivery.data.entity.ClientUserEntity
import com.bunbeauty.food_delivery.data.entity.CompanyEntity
import com.bunbeauty.food_delivery.data.model.client_user.GetClientUser
import com.bunbeauty.food_delivery.data.model.client_user.InsertClientUser
import com.bunbeauty.food_delivery.data.table.ClientUserTable
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