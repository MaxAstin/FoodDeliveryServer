package com.bunbeauty.food_delivery.service.user

import com.bunbeauty.food_delivery.data.model.user.GetUser
import com.bunbeauty.food_delivery.data.model.user.PostUserAuth
import com.bunbeauty.food_delivery.data.model.user.PostUser

interface IUserService {

    suspend fun createUser(postUser: PostUser): GetUser
    suspend fun getToken(postUserAuth: PostUserAuth): String?
}