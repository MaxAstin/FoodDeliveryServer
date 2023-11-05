package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.model.category.GetCategory
import com.bunbeauty.fooddelivery.domain.model.category.PatchCategory
import com.bunbeauty.fooddelivery.domain.model.category.PostCategory
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

private fun Routing.getCategories() {

    val categoryService: ICategoryService by inject()

    get("/category") {
        safely {
            val companyUuid = call.parameters[COMPANY_UUID_PARAMETER] ?: error("$COMPANY_UUID_PARAMETER is required")
            val categoryList = categoryService.getCategoryListByCompanyUuid(companyUuid)
            call.respondOk(categoryList)
        }
    }
}

private fun Route.postCategory() {

    val categoryService: ICategoryService by inject()

    post("/category") {
        managerWithBody<PostCategory, GetCategory> { bodyRequest ->
            categoryService.createCategory(bodyRequest.body, bodyRequest.request.jwtUser.uuid)
        }
    }
}

private fun Route.patchCategory() {

    val categoryService: ICategoryService by inject()

    patch("/category") {
        managerWithBody<PatchCategory, GetCategory> { bodyRequest ->
            val categoryUuid = call.parameters[UUID_PARAMETER] ?: error("$UUID_PARAMETER is required")
            categoryService.updateCategory(categoryUuid, bodyRequest.body)
        }
    }
}