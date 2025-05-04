package com.bunbeauty.fooddelivery.domain.feature.clientuser.usecase

import com.bunbeauty.fooddelivery.data.repo.ClientUserRepository
import com.bunbeauty.fooddelivery.domain.feature.user.model.domain.NotificationData
import java.util.UUID

class UpdateNotificationTokenUseCase(
    private val clientUserRepository: ClientUserRepository
) {
    suspend operator fun invoke(
        uuid: UUID,
        notificationData: NotificationData
    ) {
        clientUserRepository.updateNotificationToken(
            uuid = uuid,
            notificationData = notificationData
        )
    }
}
