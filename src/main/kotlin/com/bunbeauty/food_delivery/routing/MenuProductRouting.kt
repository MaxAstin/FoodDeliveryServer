package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.ext.toListWrapper
import com.bunbeauty.food_delivery.service.menu_product.IMenuProductService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureMenuProductRouting() {

    routing {
        getAllMenuProducts()
    }
}

fun Routing.getAllMenuProducts() {

    val menuProductService: IMenuProductService by inject()
    get("/menu_product") {
        val menuProductList = menuProductService.getMenuProductList()
        call.respond(HttpStatusCode.OK, menuProductList.toListWrapper())
    }
}