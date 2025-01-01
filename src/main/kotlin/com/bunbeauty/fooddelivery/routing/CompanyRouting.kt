package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.company.CompanyService
import com.bunbeauty.fooddelivery.domain.model.company.GetCompany
import com.bunbeauty.fooddelivery.domain.model.company.PatchCompany
import com.bunbeauty.fooddelivery.domain.model.company.PostCompany
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.getResult
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCompanyRouting() {

    routing {
        getWorkInfo()

        authenticate {
            createCompany()
            patchCompany()
        }
    }
}

private fun Route.createCompany() {

    val companyService: CompanyService by inject()

    post("/company") {
        adminWithBody<PostCompany, GetCompany> { bodyRequest ->
            companyService.createCompany(bodyRequest.body)
        }
    }
}

private fun Route.patchCompany() {

    val companyService: CompanyService by inject()

    patch("/company") {
        managerWithBody<PatchCompany, GetCompany> { bodyRequest ->
            val companyUuid = call.getParameter(COMPANY_UUID_PARAMETER)
            companyService.changeCompanyByUuid(
                companyUuid = companyUuid,
                patchCompany = bodyRequest.body
            )
        }
    }
}

/**
 * endpoint which describe cafe work
 * */
private fun Route.getWorkInfo() {

    val companyService: CompanyService by inject()

    get("/work_info") {
        getResult {
            val companyUuid = call.getParameter(COMPANY_UUID_PARAMETER)
            companyService.getWorkInfo(companyUuid = companyUuid)
        }
    }
}