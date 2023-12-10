package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.GetCategory
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.PatchCategory
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.PostCategory
import com.bunbeauty.fooddelivery.domain.feature.menu.service.CategoryService
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondOkWithList
import com.bunbeauty.fooddelivery.routing.extension.safely
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

    val categoryService: CategoryService by inject()

    get("/category") {
        safely {
            val companyUuid = call.getParameter(COMPANY_UUID_PARAMETER)
            val categoryList = categoryService.getCategoryListByCompanyUuid(companyUuid)
            call.respondOkWithList(categoryList)
        }
    }
}

private fun Route.postCategory() {

    val categoryService: CategoryService by inject()

    post("/category") {
        managerWithBody<PostCategory, GetCategory> { bodyRequest ->
            categoryService.createCategory(bodyRequest.body, bodyRequest.request.jwtUser.uuid)
        }
    }
}

private fun Route.patchCategory() {

    val categoryService: CategoryService by inject()

    patch("/category") {
        managerWithBody<PatchCategory, GetCategory> { bodyRequest ->
            val categoryUuid = call.getParameter(UUID_PARAMETER)
            categoryService.updateCategory(categoryUuid, bodyRequest.body)
        }
    }
}