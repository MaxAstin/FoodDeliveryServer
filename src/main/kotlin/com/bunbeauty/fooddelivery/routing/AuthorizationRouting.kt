package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.data.model.client_user.login.GetAuthSessionUuid
import com.bunbeauty.fooddelivery.data.model.client_user.login.PostClientCodeRequest
import com.bunbeauty.fooddelivery.routing.extension.clientIp
import com.bunbeauty.fooddelivery.routing.extension.clientWithBody
import com.bunbeauty.fooddelivery.routing.extension.withBody
import com.bunbeauty.fooddelivery.service.AuthorizationService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureAuthorizationRouting() {

    routing {
        sendCode()
    }
}

private fun Routing.sendCode() {

    val authorizationService: AuthorizationService by inject()

    post("/client/code_request") {
        withBody<PostClientCodeRequest, GetAuthSessionUuid> { postClientCodeRequest ->
            val companyUuid = call.parameters[Constants.COMPANY_UUID_PARAMETER]
                ?: error("${Constants.COMPANY_UUID_PARAMETER} is required")
            authorizationService.sendCode(
                companyUuid = companyUuid,
                postClientCodeRequest = postClientCodeRequest,
                clientIp = clientIp
            )
        }
    }

}