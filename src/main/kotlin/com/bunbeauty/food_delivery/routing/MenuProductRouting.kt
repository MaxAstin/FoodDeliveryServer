package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.ext.toListWrapper
import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.food_delivery.data.model.menu_product.PostMenuProduct
import com.bunbeauty.food_delivery.service.menu_product.IMenuProductService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureMenuProductRouting() {

    routing {
        getAllMenuProducts()
        authenticate {
            postMenuProduct()
        }
    }
}

fun Routing.getAllMenuProducts() {

    val menuProductService: IMenuProductService by inject()

    get("/menu_product") {
        val companyUuid = call.parameters["companyUuid"]
        if (companyUuid == null) {
            call.respond(HttpStatusCode.BadRequest, "Parameter companyUuid = null")
        } else {
            val menuProductList = menuProductService.getMenuProductListByCompanyUuid(companyUuid)
            call.respond(HttpStatusCode.OK, menuProductList.toListWrapper())
        }
    }
}

fun Route.postMenuProduct() {

    val menuProductService: IMenuProductService by inject()

    post("/menu_product") {
        managerPost<PostMenuProduct, GetMenuProduct> { jwtUser, postMenuProduct ->
            menuProductService.createMenuProduct(postMenuProduct, jwtUser.uuid)
        }
    }
}