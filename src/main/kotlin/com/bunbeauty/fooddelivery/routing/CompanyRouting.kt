package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.company.PatchCompany
import com.bunbeauty.fooddelivery.data.model.company.PostCompany
import com.bunbeauty.fooddelivery.routing.extension.adminWithBody
import com.bunbeauty.fooddelivery.service.company.ICompanyService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCompanyRouting() {

    routing {
        authenticate {
            createCompany()
            changeCompany()
        }
    }
}

fun Route.createCompany() {

    val companyService: ICompanyService by inject()

    post("/company") {
        adminWithBody<PostCompany, GetCompany> { bodyRequest ->
            companyService.createCompany(bodyRequest.body)
        }
    }
}

fun Route.changeCompany() {

    val companyService: ICompanyService by inject()

    patch("/company") {
        adminWithBody<PatchCompany, GetCompany>(COMPANY_UUID_PARAMETER) { bodyRequest ->
            val companyUuid = bodyRequest.request.parameterMap[COMPANY_UUID_PARAMETER]!!
            companyService.changeCompanyByUuid(companyUuid, bodyRequest.body)
        }
    }
}