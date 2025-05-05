package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.domain.model.notification.PostNotification
import com.bunbeauty.fooddelivery.routing.extension.clientWithBody
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import com.bunbeauty.fooddelivery.service.NotificationService
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureNotificationRouting() {
    routing {
        authenticate {
            postNotification()
            postClientNotification()
        }
    }
}

private fun Route.postNotification() {
    val notificationService: NotificationService by inject()

    post("/notification") {
        managerWithBody<PostNotification, String> { bodyRequest ->
            notificationService.sendTopicNotification(
                userUuid = bodyRequest.request.jwtUser.uuid,
                postNotification = bodyRequest.body
            )
        }
    }
}

private fun Route.postClientNotification() {
    val notificationService: NotificationService by inject()

    post("client/notification") {
        clientWithBody<PostNotification, Unit> { bodyRequest ->
            notificationService.sendClientNotification(
                clientUuid = bodyRequest.request.jwtUser.uuid,
                postNotification = bodyRequest.body
            )
        }
    }
}
