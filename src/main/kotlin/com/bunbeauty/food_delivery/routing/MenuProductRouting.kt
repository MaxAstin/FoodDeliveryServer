package com.bunbeauty.food_delivery.routing

import com.bunbeauty.food_delivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.food_delivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.food_delivery.data.model.menu_product.PostMenuProduct
import com.bunbeauty.food_delivery.service.menu_product.IMenuProductService
import io.ktor.application.*
import io.ktor.auth.*
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
        safely(COMPANY_UUID_PARAMETER) { parameterMap ->
            val companyUuid = parameterMap[COMPANY_UUID_PARAMETER]!!
            val menuProductList = menuProductService.getMenuProductListByCompanyUuid(companyUuid)
            call.respondOk(menuProductList)
        }
    }
}

fun Route.postMenuProduct() {

    val menuProductService: IMenuProductService by inject()

    post("/menu_product") {
        managerWithBody<PostMenuProduct, GetMenuProduct> { bodyRequest ->
            menuProductService.createMenuProduct(bodyRequest.body, bodyRequest.request.jwtUser.uuid)
        }
    }
}