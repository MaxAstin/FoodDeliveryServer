package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.model.client_user.ClientAuthResponse
import com.bunbeauty.fooddelivery.domain.model.client_user.login.GetClientAuthSessionUuid
import com.bunbeauty.fooddelivery.domain.model.client_user.login.GetTestClientUserPhone
import com.bunbeauty.fooddelivery.domain.model.client_user.login.PostClientCodeRequest
import com.bunbeauty.fooddelivery.domain.model.client_user.login.PostTestClientUserPhone
import com.bunbeauty.fooddelivery.domain.model.client_user.login.PutClientCode
import com.bunbeauty.fooddelivery.routing.extension.admin
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.routing.extension.clientIp
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.routing.extension.withBody
import com.bunbeauty.fooddelivery.service.AuthorizationService
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureAuthorizationRouting() {
    routing {
        sendCode()
        checkCode()
        resendCode()
        authenticate {
            postTestClientUserPhone()
            getTestClientUserPhones()
        }
    }
}

private fun Routing.sendCode() {
    val authorizationService: AuthorizationService by inject()

    post("/client/code_request") {
        withBody<PostClientCodeRequest, GetClientAuthSessionUuid> { postClientCodeRequest ->
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
            val uuid = call.getParameter(UUID_PARAMETER)
            authorizationService.checkCode(uuid, putClientCode)
        }
    }
}

private fun Routing.resendCode() {
    val authorizationService: AuthorizationService by inject()

    put("/client/code_resend") {
        safely {
            val uuid = call.getParameter(UUID_PARAMETER)
            authorizationService.resendCode(uuid = uuid, clientIp = clientIp)
            call.respondOk()
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
