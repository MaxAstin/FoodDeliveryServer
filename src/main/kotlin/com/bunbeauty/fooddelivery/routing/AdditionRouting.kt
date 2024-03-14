package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.*
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.GetMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.service.AdditionService
import com.bunbeauty.fooddelivery.routing.extension.getManagerWithListResult
import com.bunbeauty.fooddelivery.routing.extension.managerWithBody
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureAdditionRouting() {
    routing {
        authenticate {
            postAdditionGroup()
            postAdditionGroupToMenuProducts()
            getAdditionGroups()

            postAddition()
            postAdditionToGroup()
            getAdditions()
        }
    }
}

private fun Route.postAdditionGroup() {

    val additionService: AdditionService by inject()

    post("/addition_group") {
        managerWithBody<PostAdditionGroup, GetAdditionGroup> { bodyRequest ->
            additionService.createAdditionGroup(
                postAdditionGroup = bodyRequest.body,
                creatorUuid = bodyRequest.request.jwtUser.uuid
            )
        }
    }
}

private fun Route.postAdditionGroupToMenuProducts() {

    val additionService: AdditionService by inject()

    post("/addition_group_to_menu_products") {
        managerWithBody<PostAdditionGroupToMenuProducts, List<GetMenuProduct>> { bodyRequest ->
            additionService.addAdditionGroupToMenuProducts(
                postAdditionGroupToMenuProducts = bodyRequest.body,
                creatorUuid = bodyRequest.request.jwtUser.uuid
            )
        }
    }
}

private fun Route.getAdditionGroups() {

    val additionService: AdditionService by inject()

    get("/addition_group") {
        getManagerWithListResult { request ->
            additionService.getAdditionGroups(creatorUuid = request.jwtUser.uuid)
        }
    }
}

private fun Route.postAddition() {

    val additionService: AdditionService by inject()

    post("/addition") {
        managerWithBody<PostAddition, GetAddition> { bodyRequest ->
            additionService.createAddition(
                postAddition = bodyRequest.body,
                creatorUuid = bodyRequest.request.jwtUser.uuid
            )
        }
    }
}

private fun Route.postAdditionToGroup() {

    val additionService: AdditionService by inject()

    post("/addition_to_group") {
        managerWithBody<PostAdditionToGroup, List<GetMenuProduct>> { bodyRequest ->
            additionService.addAdditionToAdditionGroup(
                postAdditionToGroup = bodyRequest.body,
                creatorUuid = bodyRequest.request.jwtUser.uuid
            )
        }
    }
}

private fun Route.getAdditions() {

    val additionService: AdditionService by inject()

    get("/addition") {
        getManagerWithListResult { request ->
            additionService.getAddition(creatorUuid = request.jwtUser.uuid)
        }
    }
}
