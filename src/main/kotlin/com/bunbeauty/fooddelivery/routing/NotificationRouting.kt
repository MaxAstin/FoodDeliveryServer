package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.model.notification.PostNotification
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import com.bunbeauty.fooddelivery.service.NotificationService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureNotificationRouting() {

    routing {
        authenticate {
            postNotification()
        }
    }
}

fun Route.postNotification() {

    val notificationService: NotificationService by inject()

    post("/notification") {
        managerWithBody<PostNotification, String> { bodyRequest ->
            notificationService.sendNotification(bodyRequest.request.jwtUser.uuid, bodyRequest.body)
        }
    }

}
