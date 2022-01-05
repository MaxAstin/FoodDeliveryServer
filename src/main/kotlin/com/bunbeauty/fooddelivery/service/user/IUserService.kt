package com.bunbeauty.fooddelivery.service.user

import com.bunbeauty.fooddelivery.data.model.user.UserAuthResponse
import com.bunbeauty.fooddelivery.data.model.user.GetUser
import com.bunbeauty.fooddelivery.data.model.user.PostUserAuth
import com.bunbeauty.fooddelivery.data.model.user.PostUser

interface IUserService {

    suspend fun createUser(postUser: PostUser): GetUser
    suspend fun login(postUserAuth: PostUserAuth): UserAuthResponse?
}