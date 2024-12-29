package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.*
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.GetMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.service.AdditionService
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.managerGetListResult
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
            patchAdditionGroup()

            postAddition()
            postAdditionToGroup()
            getAdditions()
            patchAddition()
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
        managerGetListResult { request ->
            additionService.getAdditionGroups(creatorUuid = request.jwtUser.uuid)
        }
    }
}

private fun Route.patchAdditionGroup() {
    val additionService: AdditionService by inject()

    patch("/addition_group") {
        managerWithBody<PatchAdditionGroup, GetAdditionGroup> { bodyRequest ->
            val additionGroupUuid = call.getParameter(UUID_PARAMETER)
            additionService.patchAdditionGroup(
                creatorUuid = bodyRequest.request.jwtUser.uuid,
                additionGroupUuid = additionGroupUuid,
                patchAdditionGroup = bodyRequest.body
            )
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
        managerGetListResult { request ->
            additionService.getAddition(creatorUuid = request.jwtUser.uuid)
        }
    }
}

private fun Route.patchAddition() {
    val additionService: AdditionService by inject()

    patch("/addition") {
        managerWithBody<PatchAddition, GetAddition> { bodyRequest ->
            val additionGroupUuid = call.getParameter(UUID_PARAMETER)
            additionService.patchAddition(
                creatorUuid = bodyRequest.request.jwtUser.uuid,
                additionUuid = additionGroupUuid,
                patchAddition = bodyRequest.body
            )
        }
    }
}
