package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.model.company.GetCompany
import com.bunbeauty.fooddelivery.data.model.company.PostCompany
import com.bunbeauty.fooddelivery.service.company.ICompanyService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCompanyRouting() {

    routing {
        //createCompanyWithoutAuth()
        authenticate {
            createCompany()
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

fun Route.createCompanyWithoutAuth() {

    val companyService: ICompanyService by inject()

    post("/company") {
        safely {
            val postCompany: PostCompany = call.receive()
            val company = companyService.createCompany(postCompany)
            call.respond(HttpStatusCode.Created, company)
        }
    }
}