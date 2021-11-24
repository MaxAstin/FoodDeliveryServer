package com.bunbeauty.food_delivery.service.user

import com.bunbeauty.food_delivery.data.model.company.GetCompany
import com.bunbeauty.food_delivery.data.model.company.PostCompany
import com.bunbeauty.food_delivery.data.model.user.GetUser
import com.bunbeauty.food_delivery.data.model.user.PostAuth
import com.bunbeauty.food_delivery.data.model.user.PostUser

interface IUserService {

    suspend fun createUser(creatorUuid: String, postUser: PostUser): GetUser?
    suspend fun createUser(postUser: PostUser): GetUser
    suspend fun getToken(postAuth: PostAuth): String?
}