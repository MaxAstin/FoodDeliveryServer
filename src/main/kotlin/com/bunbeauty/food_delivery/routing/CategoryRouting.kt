package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.ext.toListWrapper
import com.bunbeauty.food_delivery.service.cafe.ICafeService
import com.bunbeauty.food_delivery.service.category.ICategoryService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureCategoryRouting() {

    routing {
        getCategories()
    }
}

fun Routing.getCategories() {

    val categoryService: ICategoryService by inject()

    get("/category") {
        safely {
            val categoryList = categoryService.getCategoryList()
            call.respond(HttpStatusCode.OK, categoryList.toListWrapper())
        }
    }
}