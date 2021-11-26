package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.model.company.PostCompany
import com.bunbeauty.food_delivery.service.company.ICompanyService
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
        safelyWithAuth { jwtUser ->
            if (jwtUser.isAdmin()) {
                val postCompany: PostCompany = call.receive()
                val company = companyService.createCompany(postCompany)
                call.respond(HttpStatusCode.Created, company)
            } else {
                call.respond(HttpStatusCode.Forbidden)
            }
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