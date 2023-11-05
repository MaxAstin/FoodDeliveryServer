package com.bunbeauty.fooddelivery.service.user

import com.bunbeauty.fooddelivery.domain.model.user.GetUser
import com.bunbeauty.fooddelivery.domain.model.user.PostUser
import com.bunbeauty.fooddelivery.domain.model.user.PostUserAuth
import com.bunbeauty.fooddelivery.domain.model.user.UserAuthResponse

interface IUserService {

    suspend fun createUser(postUser: PostUser): GetUser
    suspend fun login(postUserAuth: PostUserAuth): UserAuthResponse?
}