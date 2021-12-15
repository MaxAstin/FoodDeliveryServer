package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.category.GetCategory
import com.bunbeauty.fooddelivery.data.model.category.PostCategory
import com.bunbeauty.fooddelivery.service.category.ICategoryService
import io.ktor.application.*
import io.ktor.auth.*
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
        safely(COMPANY_UUID_PARAMETER) { parameterMap ->
            val companyUuid = parameterMap[COMPANY_UUID_PARAMETER]!!
            val categoryList = categoryService.getCategoryListByCompanyUuid(companyUuid)
            call.respondOk(categoryList)
        }
    }
}

fun Route.postCategory() {

    val categoryService: ICategoryService by inject()

    post("/category") {
        managerWithBody<PostCategory, GetCategory> { bodyRequest ->
            categoryService.createCategory(bodyRequest.body, bodyRequest.request.jwtUser.uuid)
        }
    }
}