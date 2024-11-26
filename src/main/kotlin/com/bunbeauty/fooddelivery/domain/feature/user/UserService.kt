package com.bunbeauty.fooddelivery.domain.feature.user

import at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST
import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.enums.UserRole
import com.bunbeauty.fooddelivery.data.features.user.UserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.user.mapper.toGetUser
import com.bunbeauty.fooddelivery.domain.feature.user.mapper.toNotificationData
import com.bunbeauty.fooddelivery.domain.feature.user.model.api.PutNotificationToken
import com.bunbeauty.fooddelivery.domain.feature.user.model.api.PutUnlimitedNotification
import com.bunbeauty.fooddelivery.domain.model.user.*
import com.bunbeauty.fooddelivery.domain.toUuid
import com.toxicbakery.bcrypt.Bcrypt
import com.toxicbakery.bcrypt.Bcrypt.verify
import org.joda.time.DateTime

class UserService(
    private val userRepository: UserRepository,
    private val jwtService: IJwtService,
) {

    suspend fun login(postUserAuth: PostUserAuth): UserAuthResponse {
        val user = userRepository.getUserByUsername(
            username = postUserAuth.username.lowercase()
        ) ?: incorrectUsernameError(postUserAuth.username)

        val isSuccess = verify(
            input = postUserAuth.password,
            expected = user.passwordHash.toByteArray()
        )
        return if (isSuccess) {
            val token = jwtService.generateToken(user)
            UserAuthResponse(
                token = token,
                cityUuid = user.cityWithCafes.city.uuid,
                companyUuid = user.companyUuid
            )
        } else {
            incorrectPasswordError(postUserAuth.password)
        }
    }

    suspend fun createUser(postUser: PostUser): GetUser {
        val passwordHash = String(Bcrypt.hash(postUser.password, MIN_COST))
        val insertUser = InsertUser(
            username = postUser.username.lowercase(),
            passwordHash = passwordHash,
            role = UserRole.findByRoleName(postUser.role),
            cityUuid = postUser.cityUuid.toUuid()
        )

        return userRepository.insertUser(
            insertUser = insertUser
        ).toGetUser()
    }

    suspend fun updateNotificationToken(
        userUuid: String,
        putNotificationToken: PutNotificationToken
    ) {
        userRepository.updateNotificationToken(
            uuid = userUuid.toUuid(),
            notificationData = putNotificationToken.toNotificationData(
                dateTime = getUpdateTokenDateTime()
            )
        ).orThrowNotFoundByUuidError(uuid = userUuid)
    }

    suspend fun clearNotificationToken(userUuid: String) {
        userRepository.clearNotificationToken(
            uuid = userUuid.toUuid(),
            dateTime = getUpdateTokenDateTime()
        ).orThrowNotFoundByUuidError(uuid = userUuid)
    }

    suspend fun updateUnlimitedNotification(
        userUuid: String,
        putUnlimitedNotification: PutUnlimitedNotification
    ) {
        userRepository.updateUnlimitedNotification(
            uuid = userUuid.toUuid(),
            isEnabled = putUnlimitedNotification.isEnabled
        ).orThrowNotFoundByUuidError(uuid = userUuid)
    }

    suspend fun getUser(userUuid: String): GetUser {
        return userRepository.getUserByUuid(
            uuid = userUuid.toUuid()
        )?.toGetUser()
            .orThrowNotFoundByUuidError(uuid = userUuid)
    }

    private fun incorrectUsernameError(username: String): Nothing {
        error("Incorrect username: $username")
    }

    private fun incorrectPasswordError(password: String): Nothing {
        error("Incorrect password: $password")
    }

    private fun getUpdateTokenDateTime(): String {
        return DateTime.now().toString("dd.MM.yyyy HH:mm:ss")
    }

}