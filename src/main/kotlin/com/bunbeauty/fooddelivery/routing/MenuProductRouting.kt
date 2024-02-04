package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.GetMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.PatchMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.PostMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.service.MenuProductService
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import com.bunbeauty.fooddelivery.routing.extension.respondOkWithList
import com.bunbeauty.fooddelivery.routing.extension.safely
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureMenuProductRouting() {

    routing {
        getAllMenuProductsTest()
        getAllMenuProducts()
        authenticate {
            postMenuProduct()
            patchMenuProduct()
        }
    }
}

private fun Routing.getAllMenuProductsTest() {

    val menuProductService: MenuProductService by inject()

    get("/menu_product/test") {
        safely {
            val companyUuid = call.getParameter(COMPANY_UUID_PARAMETER)
            val menuProductList = menuProductService.getMenuProductListByCompanyUuidTest(companyUuid)
            call.respondOkWithList(menuProductList)
        }
    }
}

private fun Routing.getAllMenuProducts() {

    val menuProductService: MenuProductService by inject()

    get("/menu_product") {
        safely {
            val companyUuid = call.getParameter(COMPANY_UUID_PARAMETER)
            val menuProductList = menuProductService.getMenuProductListByCompanyUuid(companyUuid)
            call.respondOkWithList(menuProductList)
        }
    }
}

private fun Route.postMenuProduct() {

    val menuProductService: MenuProductService by inject()

    post("/menu_product") {
        managerWithBody<PostMenuProduct, GetMenuProduct> { bodyRequest ->
            menuProductService.createMenuProduct(bodyRequest.body, bodyRequest.request.jwtUser.uuid)
        }
    }
}

private fun Route.patchMenuProduct() {

    val menuProductService: MenuProductService by inject()

    patch("/menu_product") {
        managerWithBody<PatchMenuProduct, GetMenuProduct> { bodyRequest ->
            val menuProductUuid = call.getParameter(UUID_PARAMETER)
            menuProductService.updateMenuProduct(menuProductUuid, bodyRequest.body)
        }
    }
}