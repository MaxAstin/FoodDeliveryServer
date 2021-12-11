package com.bunbeauty.food_delivery.service.client_user

import com.bunbeauty.food_delivery.auth.IJwtService
import com.bunbeauty.food_delivery.data.ext.toUuid
import com.bunbeauty.food_delivery.data.model.client_user.GetClientUser
import com.bunbeauty.food_delivery.data.model.client_user.InsertClientUser
import com.bunbeauty.food_delivery.data.model.client_user.PostClientUserAuth
import com.bunbeauty.food_delivery.data.repo.client_user.IClientUserRepository
import com.google.firebase.auth.FirebaseAuth

class ClientUserService(
    private val firebaseAuth: FirebaseAuth,
    private val clientUserRepository: IClientUserRepository,
    private val jwtService: IJwtService,
) : IClientUserService {

    override suspend fun getToken(clientUserAuth: PostClientUserAuth): String? {
        val firebaseUser = firebaseAuth.getUser(clientUserAuth.firebaseUuid)
        return if (firebaseUser.phoneNumber == clientUserAuth.phoneNumber) {
            var getClientUser = clientUserRepository.getClientUserByPhoneNumber(clientUserAuth.phoneNumber)
            if (getClientUser == null) {
                val insertClientUser = InsertClientUser(
                    phoneNumber = clientUserAuth.phoneNumber,
                    email = null,
                    companyUuid = clientUserAuth.companyUuid.toUuid(),
                )
                getClientUser = clientUserRepository.insertClientUser(insertClientUser)
            }

            jwtService.generateToken(getClientUser)
        } else {
            null
        }
    }

    override suspend fun getClientUserByUuid(clientUserUuid: String): GetClientUser? {
        return clientUserRepository.getClientUserByUuid(clientUserUuid.toUuid())
    }

}