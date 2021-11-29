package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.ext.toListWrapper
import com.bunbeauty.food_delivery.data.model.category.GetCategory
import com.bunbeauty.food_delivery.data.model.category.PostCategory
import com.bunbeauty.food_delivery.service.category.ICategoryService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCategoryRouting() {

    routing {
        getCategories()
        authenticate {
            postCategory()
        }
    }
}

fun Routing.getCategories() {

    val categoryService: ICategoryService by inject()

    get("/category") {
        safely {
            val companyUuid = call.parameters["companyUuid"]
            if (companyUuid == null) {
                call.respond(HttpStatusCode.BadRequest, "Parameter companyUuid = null")
            } else {
                val categoryList = categoryService.getCategoryListByCompanyUuid(companyUuid)
                call.respond(HttpStatusCode.OK, categoryList.toListWrapper())
            }
        }
    }
}

fun Route.postCategory() {

    val categoryService: ICategoryService by inject()

    post("/category") {
        managerPost<PostCategory, GetCategory> { jwtUser, postCategory ->
            categoryService.createCategory(postCategory, jwtUser.uuid)
        }
    }
}