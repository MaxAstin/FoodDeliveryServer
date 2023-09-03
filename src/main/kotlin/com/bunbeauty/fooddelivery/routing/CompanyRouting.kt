package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.company.PatchCompany
import com.bunbeauty.fooddelivery.data.model.company.PostCompany
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.service.company.ICompanyService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCompanyRouting() {

    routing {
        authenticate {
            createCompany()
            changeCompany()
        }
    }
}

private fun Route.createCompany() {

    val companyService: ICompanyService by inject()

    post("/company") {
        adminWithBody<PostCompany, GetCompany> { bodyRequest ->
            companyService.createCompany(bodyRequest.body)
        }
    }
}

private fun Route.changeCompany() {

    val companyService: ICompanyService by inject()

    patch("/company") {
        adminWithBody<PatchCompany, GetCompany> { bodyRequest ->
            val companyUuid = call.parameters[COMPANY_UUID_PARAMETER] ?: error("$COMPANY_UUID_PARAMETER is required")
            companyService.changeCompanyByUuid(companyUuid, bodyRequest.body)
        }
    }
}