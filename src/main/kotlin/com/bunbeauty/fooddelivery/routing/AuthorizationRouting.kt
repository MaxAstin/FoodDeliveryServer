package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.data.model.client_user.ClientAuthResponse
import com.bunbeauty.fooddelivery.data.model.client_user.login.*
import com.bunbeauty.fooddelivery.routing.extension.admin
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.routing.extension.clientIp
import com.bunbeauty.fooddelivery.routing.extension.withBody
import com.bunbeauty.fooddelivery.service.AuthorizationService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureAuthorizationRouting() {

    routing {
        sendCode()
        checkCode()
        authenticate {
            postTestClientUserPhone()
            getTestClientUserPhones()
        }
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

private fun Routing.checkCode() {

    val authorizationService: AuthorizationService by inject()

    put("/client/code_check") {
        withBody<PutClientCode, ClientAuthResponse> { putClientCode ->
            val uuid = call.parameters[Constants.UUID_PARAMETER] ?: error("${Constants.UUID_PARAMETER} is required")
            authorizationService.checkCode(uuid, putClientCode)
        }
    }
}

private fun Route.postTestClientUserPhone() {

    val authorizationService: AuthorizationService by inject()

    post("/test_phone") {
        adminWithBody<PostTestClientUserPhone, GetTestClientUserPhone> { bodyRequest ->
            authorizationService.createTestClientUserPhone(bodyRequest.body)
        }
    }
}

private fun Route.getTestClientUserPhones() {

    val authorizationService: AuthorizationService by inject()

    get("/test_phone") {
        admin {
            authorizationService.getTestClientUserPhoneList()
        }
    }
}