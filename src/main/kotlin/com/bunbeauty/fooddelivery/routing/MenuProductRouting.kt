package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.model.menu_product.GetMenuProduct
import com.bunbeauty.fooddelivery.data.model.menu_product.PatchMenuProduct
import com.bunbeauty.fooddelivery.data.model.menu_product.PostMenuProduct
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.menu_product.IMenuProductService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureMenuProductRouting() {

    routing {
        getAllMenuProducts()
        authenticate {
            postMenuProduct()
            patchMenuProduct()
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

fun Route.patchMenuProduct() {

    val menuProductService: IMenuProductService by inject()

    patch("/menu_product") {
        managerWithBody<PatchMenuProduct, GetMenuProduct>(UUID_PARAMETER) { bodyRequest ->
            val menuProductUuid = bodyRequest.request.parameterMap[UUID_PARAMETER]!!
            menuProductService.updateMenuProduct(menuProductUuid, bodyRequest.body)
        }
    }
}