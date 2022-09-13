package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.category.GetCategory
import com.bunbeauty.fooddelivery.data.model.category.PatchCategory
import com.bunbeauty.fooddelivery.data.model.category.PostCategory
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.category.ICategoryService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCategoryRouting() {

    routing {
        getCategories()
        authenticate {
            postCategory()
            patchCategory()
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

fun Route.patchCategory() {

    val categoryService: ICategoryService by inject()

    patch("/category") {
        managerWithBody<PatchCategory, GetCategory>(UUID_PARAMETER) { bodyRequest ->
            val categoryUuid = bodyRequest.request.parameterMap[UUID_PARAMETER]!!
            categoryService.updateCategory(categoryUuid, bodyRequest.body)
        }
    }
}